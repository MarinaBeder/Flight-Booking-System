package com.FlighSystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
  

//@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException{

	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public NotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	/*
	 * public NotFoundException (String message) { super(message); // TODO
	 * Auto-generated constructor stub }
	 */
	
	
}
