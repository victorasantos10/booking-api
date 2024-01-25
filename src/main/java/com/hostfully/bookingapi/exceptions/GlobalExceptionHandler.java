package com.hostfully.bookingapi.exceptions;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import jakarta.persistence.EntityNotFoundException;
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

    @ExceptionHandler({OverlappingDatesException.class, ExistingBlockException.class, ExistingBookingException.class})
    public ResponseEntity<Object> handleOverlappingDatesException(OverlappingDatesException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(exception.getMessage()))));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(exception.getMessage()))));
    }


}
