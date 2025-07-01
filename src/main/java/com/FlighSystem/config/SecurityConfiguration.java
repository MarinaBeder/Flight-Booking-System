package com.FlighSystem.config;

import org.springframework.context.annotation.Bean;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

private final JwtAuthenticationFilter jwtAuthFilter;
private final AuthenticationProvider authenticationProvider;

private final LogoutHandler logoutHandler;

public SecurityConfiguration(JwtAuthenticationFilter jwtAuthFilter, AuthenticationProvider authenticationProvider,
		LogoutHandler logoutHandler) {
	this.jwtAuthFilter = jwtAuthFilter;
	this.authenticationProvider = authenticationProvider;
	this.logoutHandler = logoutHandler;
}



@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	
	http
	.exceptionHandling()
    .authenticationEntryPoint(authenticationEntryPoint()) // Custom AuthenticationEntryPoint
    .and()
	.csrf()
	 
	.disable()
	.authorizeHttpRequests()
	.requestMatchers("/api/v1/user/**",
			 "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
				
			)
	.permitAll().requestMatchers("/error/**").permitAll()  // Allow access to Spring Boot error handling
	.anyRequest()
	.authenticated()
	.and()
	.sessionManagement()//should be stateless session
	.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	.and()
	.authenticationProvider(authenticationProvider)
	.addFilterBefore(jwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
	
	.logout()
	.logoutUrl("/api/v1/auth/logout")
.addLogoutHandler(logoutHandler)
.logoutSuccessHandler(
		      (request,response,authentication)->
              SecurityContextHolder.clearContext());
	
	return http.build();
	
}
	
@Bean
public AuthenticationEntryPoint authenticationEntryPoint() {
    return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
}	
	
}
