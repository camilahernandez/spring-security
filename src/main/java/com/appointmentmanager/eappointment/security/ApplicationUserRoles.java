package com.appointmentmanager.eappointment.security;

public enum ApplicationUserRoles {

	HOST,
	GUEST;
	
	public static ApplicationUserRoles fromString(String role) {
		for(ApplicationUserRoles r : ApplicationUserRoles.values()) {
			if(r.toString().equalsIgnoreCase(role)) {
				return r;
			}
		}
		return null;
	}
}
