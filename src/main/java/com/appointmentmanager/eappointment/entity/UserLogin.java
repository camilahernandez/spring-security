package com.appointmentmanager.eappointment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.appointmentmanager.eappointment.dto.UserRegistrationRequest;

import javax.persistence.ForeignKey;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserLogin {

	@Id
	@GeneratedValue
	private Long id;
//	@OneToOne  
//	@Column(name="user_id") 
//	private Long mainUserId;  
	private String password;
	private String userName;
	private Boolean enabled;
	@ManyToOne
	@JoinColumn(name = "role_id",
	foreignKey = @ForeignKey(name="ROLE_ID_FK")
	)
	private Master master;
	public UserLogin(UserRegistrationRequest userRegistration) {
		this.userName = userRegistration.getUsername();
		this.password = userRegistration.getPassword();
		this.enabled = true;
	}
	public UserLogin() {
	}
	
	
	
	
	
}
