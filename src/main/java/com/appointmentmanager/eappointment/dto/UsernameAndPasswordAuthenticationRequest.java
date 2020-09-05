package com.appointmentmanager.eappointment.dto;

public class UsernameAndPasswordAuthenticationRequest {

	private String Username;
	private String password;
	
	
	public UsernameAndPasswordAuthenticationRequest() {
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
