package com.bnpp.kata.berlinclock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.bnpp.kata.berlinclock.model.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(TimeFormatException.class)
	public ErrorResponse handleTimeFormatException(TimeFormatException ex) {
		return new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleGenericException(Exception ex) {
		return new ErrorResponse("An unexpected error occurred.", HttpStatus.INTERNAL_SERVER_ERROR.value());
	}
}
