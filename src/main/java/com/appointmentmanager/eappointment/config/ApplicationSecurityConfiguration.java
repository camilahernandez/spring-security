package com.appointmentmanager.eappointment.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.appointmentmanager.eappointment.security.JwtConfig;
import com.appointmentmanager.eappointment.security.JwtTokenVerifier;
import com.appointmentmanager.eappointment.security.JwtUsernameAndPasswordAuthenticationFilter;
import com.appointmentmanager.eappointment.security.JwtValidationProvider;
import com.appointmentmanager.eappointment.service.impl.ApplicationUserDetailService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfiguration extends WebSecurityConfigurerAdapter{

	private final PasswordEncoder passwordEncoder;
    private final ApplicationUserDetailService applicationUserDetailService;
    private final JwtConfig jwtConfig;
    private final JwtValidationProvider jwtProvider;

    @Autowired
    public ApplicationSecurityConfiguration(PasswordEncoder passwordEncoder,
                                     ApplicationUserDetailService applicationUserDetailService
                                     ,JwtConfig jwtConfig,
                                     JwtValidationProvider jwtProvider) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserDetailService = applicationUserDetailService;
        this.jwtConfig = jwtConfig;
        this.jwtProvider = jwtProvider;
    }
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilter(new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),jwtConfig,jwtProvider))
        .addFilterAfter(new JwtTokenVerifier(jwtProvider), JwtUsernameAndPasswordAuthenticationFilter.class)
        .authorizeRequests()
        .antMatchers( "/register", "/css/*", "/js/*").permitAll()
        .anyRequest()
        .authenticated();
	}
	
	 @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserDetailService);
        return provider;
    }

}
