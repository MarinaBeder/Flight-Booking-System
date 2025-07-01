package com.FlighSystem.exceptions;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(value = { UnauthorizedException.class })
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e) {
		ApiExcepion apiException = new ApiExcepion(e.getMessage(),

				HttpStatus.UNAUTHORIZED, ZonedDateTime.now(ZoneId.of("UTC")));
		return new ResponseEntity<Object>(apiException, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}

	@ExceptionHandler(value = { NonUniqueEmailException.class })
	public ResponseEntity<Object> handleApiRequestNonUniqueUsernameException(NonUniqueEmailException e) {
		ApiExcepion apiException = new ApiExcepion(e.getMessage(),

				HttpStatus.CONFLICT, ZonedDateTime.now(ZoneId.of("UTC")));
		return new ResponseEntity<Object>(apiException, HttpStatus.CONFLICT);
	}
	
    // ✅ Handle Not Found Exception
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {

        ApiExcepion apiException = new ApiExcepion(e.getMessage(),

				HttpStatus.NOT_FOUND, ZonedDateTime.now(ZoneId.of("UTC")));
        
        return new ResponseEntity<>(apiException, HttpStatus.NOT_FOUND);
    }
    
    // ✅ Handle User Already Exists Exception
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExists(AlreadyExistException e) {
        ApiExcepion apiException = new ApiExcepion(e.getMessage(),
				HttpStatus.CONFLICT, ZonedDateTime.now(ZoneId.of("UTC")));
        return new ResponseEntity<>(apiException, HttpStatus.CONFLICT);
    }
    
    
    
    @ExceptionHandler(Exception.class)
    public void handleException(Exception e) {

        String message = "There was a problem." + 
                "Please try again later. " +
                "If the issue persists, feel free to contact support with the details of your request.";


        ApiExcepion apiException = new ApiExcepion(message,
        		 HttpStatus.INTERNAL_SERVER_ERROR, ZonedDateTime.now(ZoneId.of("UTC")));
        
    }
}
