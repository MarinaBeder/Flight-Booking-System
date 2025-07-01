package com.FlighSystem.auditing;

import java.util.Optional;



import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.FlighSystem.domain.User;

public class ApplicationAuditAware implements AuditorAware<Long>{

	@Override
	public Optional<Long> getCurrentAuditor() {
		Authentication authentication = SecurityContextHolder
				.getContext()
				.getAuthentication();
		if (authentication==null || ! authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken  ) {
			// if i cannot get the user connected 
			return Optional.empty();

			
		}
      User userPrinciple =(User)authentication.getPrincipal(); 		
		
		return Optional.ofNullable(userPrinciple.getId());

	}
	// we want to audit ID of user 

}
