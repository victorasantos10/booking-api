package com.hostfully.bookingapi.exceptions;

public class DateTimeIntervalTooShortException extends RuntimeException {
    public DateTimeIntervalTooShortException(String message) { super(message); }
}