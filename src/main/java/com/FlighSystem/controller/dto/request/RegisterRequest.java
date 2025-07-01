package com.FlighSystem.controller.dto.request;


import com.FlighSystem.domain.Address;
import com.FlighSystem.domain.Gender;
import com.FlighSystem.domain.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	
	private String fullName;
	
	@NotBlank(message = "Email must be a valid email address")
	@NotNull(message = "Email cannot be Empty")
	@Email(message = "Email must be a valid email address")
	private String email;

	@NotNull(message = "Password cannot be Empty")
    @Size(max = 120 , message = "Password must be 20 characters or less")
	@NotBlank(message = "password must be vaild")
	private String password;
    
    @Embedded
    private Address address;
    
	@Min(value = 0, message = "Age must be positive")
	private Integer age;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    
    
	@NotNull(message = "Username cannot be Empty")
    @Size(max = 20 , message = "Username must be 20 characters or less")
	private String username;
	
	@Enumerated(EnumType.STRING)
	@NotNull(message = "Role cannot be null")
	private Role role;
    
}
