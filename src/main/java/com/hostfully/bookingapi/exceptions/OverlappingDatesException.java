package com.hostfully.bookingapi.exceptions;

public class OverlappingDatesException extends RuntimeException {
    public OverlappingDatesException(String message) {
        super(message);
    }
}
