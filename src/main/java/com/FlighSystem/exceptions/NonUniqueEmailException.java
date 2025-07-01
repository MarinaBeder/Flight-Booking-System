package com.FlighSystem.exceptions;

public class NonUniqueEmailException extends RuntimeException {
	public NonUniqueEmailException(String message, Throwable cause) {
		super(message, cause);
	}

	public NonUniqueEmailException(String message) {
		super(message);
	}
}
