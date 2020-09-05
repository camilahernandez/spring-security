package com.appointmentmanager.eappointment.security;

import java.time.LocalDate;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.appointmentmanager.eappointment.service.impl.ApplicationUserDetailService;
import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@Service
public class JwtValidationProvider {
	private final JwtConfig jwtConfig;
	private final SecretKey secretKey;
	private final ApplicationUserDetailService userService;
	
	@Autowired
	public JwtValidationProvider(JwtConfig jwtConfig, SecretKey secretKey
			,ApplicationUserDetailService userService) {
		super();
		this.jwtConfig = jwtConfig;
		this.secretKey = secretKey;
		this.userService = userService;
	}
	
	public String getTokenFromRequest(HttpServletRequest request) {
		String fullToken = request.getHeader(jwtConfig.getAuthorizationHeader());
		if(Strings.isNullOrEmpty(fullToken) || !fullToken.startsWith(jwtConfig.getTokenPrefix())) {
			return "";
		}
		return fullToken.replace(jwtConfig.getTokenPrefix(), "");
		
	}
	public UserDetails getUserFromToken(String token) {
		Jws<Claims> parseClaims = Jwts.parserBuilder()
				.setSigningKey(secretKey)
				.build().parseClaimsJws(token);
		String username = parseClaims.getBody().getSubject();
		return userService.loadUserByUsername(username);
	}
	
	public String createTokenFromAuth(Authentication authResult) {
		return Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
				.signWith(secretKey)
				.compact();
	}
}
