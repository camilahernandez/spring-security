package com.appointmentmanager.eappointment.security;

import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.appointmentmanager.eappointment.entity.UserLogin;


public class ApplicationUserDetails implements UserDetails{

	private String userName;
	private String password;
	private Set<? extends GrantedAuthority> authorities;
	private Boolean enabled;
	
	
	
	@Autowired
	public ApplicationUserDetails(UserLogin user) {
		this.userName = user.getUserName();
		this.password = user.getPassword();
		this.enabled = user.getEnabled();
		ApplicationUserRoles role = ApplicationUserRoles.fromString(user.getMaster().getValue());
		this.authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + role.toString()));
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

}
