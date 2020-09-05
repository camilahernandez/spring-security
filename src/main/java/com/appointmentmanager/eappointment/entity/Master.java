package com.appointmentmanager.eappointment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="master_table")
public class Master {

	@Id
	@GeneratedValue
	private Long id;
	private String key;
	private String value;
	
	public Master(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public Master() {
	}
	
}
