package com.appointmentmanager.eappointment.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.appointmentmanager.eappointment.dto.UserRegistrationRequest;
import com.appointmentmanager.eappointment.entity.Master;
import com.appointmentmanager.eappointment.entity.UserLogin;
import com.appointmentmanager.eappointment.repository.MasterRepository;
import com.appointmentmanager.eappointment.repository.UserLoginRepository;
import com.appointmentmanager.eappointment.security.ApplicationUserDetails;


@Service
public class ApplicationUserDetailService implements UserDetailsService{

	private final UserLoginRepository userLoginRepository;
	private final MasterRepository masterRepository;
	private final PasswordEncoder passwordEncoder;
	@Autowired
	public ApplicationUserDetailService(UserLoginRepository userLoginRepository,PasswordEncoder passwordEncoder
			,MasterRepository masterRepository) {
		this.userLoginRepository = userLoginRepository;
		this.passwordEncoder = passwordEncoder;
		this.masterRepository = masterRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserLogin> userDb = userLoginRepository.findByUserName(username);
		if(!userDb.isPresent())
			throw new UsernameNotFoundException(username);
		return userDb.map(ApplicationUserDetails::new).get();
	}
	
	public boolean userNameExists(String userName) {
		return userLoginRepository.findByUserName(userName).isPresent();
	}
	
	public void signup(UserRegistrationRequest userRegistration) {
		
		userRegistration.setPassword(passwordEncoder.encode(userRegistration.getPassword()));
		UserLogin newUser = new UserLogin(userRegistration);
		
		Master role = masterRepository.findByKeyAndValue("role", userRegistration.getRole())
				.orElse( new Master("role",userRegistration.getRole()));
		if(role.getId() == null)
			masterRepository.save(role);
		
		newUser.setMaster(role);
		
		userLoginRepository.save(newUser);
	}

}
