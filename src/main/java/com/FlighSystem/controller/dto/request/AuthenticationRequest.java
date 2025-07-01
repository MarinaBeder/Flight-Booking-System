package com.FlighSystem.controller.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {

	@NotBlank(message = "Email must be a valid email address")
	@NotNull(message = "Email cannot be Empty")
	@Email(message = "Email must be a valid email address")
	private String email;

	@NotNull(message = "Password cannot be Empty")
    @Size(max = 120 , message = "Password must be 20 characters or less")
	@NotBlank(message = "password must be vaild")
	private String password;

}
