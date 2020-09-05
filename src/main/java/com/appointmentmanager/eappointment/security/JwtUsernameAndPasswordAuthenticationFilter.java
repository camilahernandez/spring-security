package com.appointmentmanager.eappointment.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.appointmentmanager.eappointment.dto.UsernameAndPasswordAuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private final AuthenticationManager authenticationManager;
	private final JwtConfig jwtConfig;
	private final JwtValidationProvider jwtProvider;
	
	
	public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager
			,JwtConfig jwtConfig,JwtValidationProvider jwtProvider
			) {
		this.authenticationManager = authenticationManager;
		this.jwtConfig = jwtConfig;
		this.jwtProvider = jwtProvider;
	}



	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UsernameAndPasswordAuthenticationRequest authRequest= new ObjectMapper()
					.readValue(request.getInputStream(),UsernameAndPasswordAuthenticationRequest.class);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(),
					authRequest.getPassword()
					);
			Authentication authenticate = authenticationManager.authenticate(authentication); 
			return authenticate;
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}



	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// create a token once the request has been succefuly authenticated
		
		String token = jwtProvider.createTokenFromAuth(authResult);
		response.addHeader(jwtConfig.getAuthorizationHeader(), jwtConfig.getTokenPrefix() + token);
		super.successfulAuthentication(request, response, chain, authResult);
	}

	
	
}
