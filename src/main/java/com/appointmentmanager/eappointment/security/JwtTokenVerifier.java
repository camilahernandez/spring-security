package com.appointmentmanager.eappointment.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.common.base.Strings;

import io.jsonwebtoken.JwtException;

@Component
public class JwtTokenVerifier extends OncePerRequestFilter{

	private final JwtValidationProvider jwtProvider;
	
	public JwtTokenVerifier(JwtValidationProvider jwtProvider) {
		this.jwtProvider = jwtProvider;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = jwtProvider.getTokenFromRequest(request);
		
		if(Strings.isNullOrEmpty(token) ) {
			filterChain.doFilter(request, response);
			return;
		}
		
		try {
			UserDetails user = jwtProvider.getUserFromToken(token);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					user.getUsername(), 
					null,user.getAuthorities()) ;
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		}catch(JwtException e) {
			throw new IllegalStateException(String.format("Token %s cannot be trusted", token));
		}
		
		filterChain.doFilter(request, response);
		
	}

}
