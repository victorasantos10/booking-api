package com.hostfully.bookingapi.handlers;

import com.hostfully.bookingapi.exceptions.ExistingBlockException;
import com.hostfully.bookingapi.exceptions.ExistingBookingException;
import com.hostfully.bookingapi.exceptions.OverlappingDatesException;
import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(exception.getMessage()))));
    }

    @ExceptionHandler({OverlappingDatesException.class, ExistingBookingException.class, ExistingBlockException.class})
    public ResponseEntity<Object> handleBusinessException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(exception.getMessage()))));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleRuntimeException(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList("Dependent entities exist. Please remove or reassign them before attempting to delete this entity."))));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList("Oops! Something went wrong. Please contact support"))));
    }


}