package com.FlighSystem.util;

import org.springframework.security.core.userdetails.UserDetails;

public class AuthorityUtil {
    public static Boolean hasRole(String role, UserDetails userDetails) {
        return userDetails.getAuthorities().stream()
            .anyMatch(auth -> auth.getAuthority().equals(role));
    }
}

