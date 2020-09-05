package com.appointmentmanager.eappointment.dto;

import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRegistrationRequest {

	private String username;
	private String password;
	@Email(message = "Please provide valid email")
	private String email;
	private String phoneNumber;
	private String role;
	
	public UserRegistrationRequest(@JsonProperty("username") String username,@JsonProperty("password") String password,
				@Email(message = "Please provide valid email")@JsonProperty("email") String email,
				@JsonProperty("ph") String phoneNumber,@JsonProperty("role") String role) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
