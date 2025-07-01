package com.FlighSystem.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1/demo-controller")
public class DemoController {
	@Operation(
	        description = "",
	        summary = "to test after user make login",
	        responses = {
	                @ApiResponse(
	                        description = "OK",
	                        responseCode = "200"
	                )
	        }

	)
	@GetMapping
	public ResponseEntity<String >sayHello(){
		return ResponseEntity.ok("hello from secured ");
	}
	
}
