package com.FlighSystem.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.FlighSystem.controller.dto.request.RegisterRequest;
import com.FlighSystem.controller.dto.response.UserResponse;
import com.FlighSystem.service.RegisterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/user")
public class RegisterController {
	   private final RegisterService registerService;

	   
		public RegisterController(RegisterService registerService) {
		this.registerService = registerService;
	}





		@PostMapping("/register")
	    public ResponseEntity<UserResponse> createUser( @Valid  @RequestBody RegisterRequest user) {
	        UserResponse createdUser = registerService.createUser(user);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
	    }
}
