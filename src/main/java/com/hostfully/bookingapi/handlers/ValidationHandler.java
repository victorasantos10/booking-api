package com.hostfully.bookingapi.handlers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
																  HttpHeaders headers, HttpStatus status, WebRequest request) {

		ArrayList<Object> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) ->{

			String _fieldName = ((FieldError) error).getField();
			String _message = error.getDefaultMessage();
			errors.add(new Object(){ final String fieldName = _fieldName; final String message = _message; });
		});
		return new ResponseEntity<>(new ApiResponseDTO<>(null, errors), HttpStatus.BAD_REQUEST);
	}
}