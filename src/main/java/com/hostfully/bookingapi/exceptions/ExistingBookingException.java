package com.hostfully.bookingapi.exceptions;

public class ExistingBookingException extends RuntimeException {

    public ExistingBookingException(String message){
        super(message);
    }
}
