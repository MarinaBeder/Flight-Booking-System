package com.FlighSystem.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public class ApiExcepion {
	private String message;
	private HttpStatus htpStatus;
	private ZonedDateTime timeStamp;

	public ApiExcepion(String message,

			HttpStatus htpStatus, ZonedDateTime timeStamp) {
		this.message = message;
		this.htpStatus = htpStatus;
		this.timeStamp = timeStamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getHtpStatus() {
		return htpStatus;
	}

	public void setHtpStatus(HttpStatus htpStatus) {
		this.htpStatus = htpStatus;
	}

	public ZonedDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(ZonedDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

}
