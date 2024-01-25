package com.hostfully.bookingapi.handlers;

import com.hostfully.bookingapi.exceptions.ExistingBlockException;
import com.hostfully.bookingapi.exceptions.ExistingBookingException;
import com.hostfully.bookingapi.exceptions.InvalidDateRangeException;
import com.hostfully.bookingapi.exceptions.OverlappingDatesException;
import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.validation.ApiResponseErrorModel;
import com.hostfully.bookingapi.models.validation.ApiValidationResponseErrorModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({EntityNotFoundException.class})
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(new ApiResponseErrorModel(exception.getMessage())))));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String,String>>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        ArrayList<ApiResponseErrorModel> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(new ApiResponseErrorModel(fieldName, errorMessage));
        });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO<>(null, errors));
    }

    @ExceptionHandler(InvalidDateRangeException.class)
    public ResponseEntity<ApiResponseDTO<Map<String,String>>> handleInvalidDateRangeException(
            InvalidDateRangeException ex) {
        ArrayList<ApiResponseErrorModel> errors = new ArrayList<>();
        errors.add(new ApiResponseErrorModel("startDateTime", "startDateTime should be before endDateTime"));
        errors.add(new ApiResponseErrorModel("endDateTime", "endDateTime should be after startDateTime"));
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST).body(new ApiResponseDTO<>(null, errors));
    }

    @ExceptionHandler({OverlappingDatesException.class, ExistingBookingException.class, ExistingBlockException.class})
    public ResponseEntity<Object> handleBusinessException(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(new ApiResponseErrorModel(exception.getMessage())))));
    }

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<Object> handleRuntimeException(DataIntegrityViolationException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(new ApiResponseErrorModel("Dependent entities exist. Please remove or reassign them before attempting to delete this entity.")))));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList(new ApiResponseErrorModel("Oops! Something went wrong. Please contact support")))));
    }


}
