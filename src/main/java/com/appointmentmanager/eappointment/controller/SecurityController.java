package com.appointmentmanager.eappointment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appointmentmanager.eappointment.dto.UserRegistrationRequest;
import com.appointmentmanager.eappointment.service.impl.ApplicationUserDetailService;

@RestController
public class SecurityController {

	private final ApplicationUserDetailService applicationUserDetailService;
	
	@Autowired
	public SecurityController(ApplicationUserDetailService applicationUserDetailService) {
		this.applicationUserDetailService = applicationUserDetailService;
	}

	@PostMapping("/register")
	public ResponseEntity<String> createUser(@RequestBody UserRegistrationRequest userRegistration) {
		if(applicationUserDetailService.userNameExists(userRegistration.getUsername()))
			throw new RuntimeException("User already exists"); // TODO create exception class
		
		
		applicationUserDetailService.signup(userRegistration);
		return new ResponseEntity<>("UserRegistration Successful",HttpStatus.OK);
	}
	
	@GetMapping("/")
	public String welcome() {
		return "welcome";
	}
	
	@PreAuthorize("hasAuthority('ROLE_HOST')")
	@GetMapping("/dummy")
	public String dummy() {
		return "this is a dummy api";
	}
	
}
