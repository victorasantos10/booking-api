package com.hostfully.bookingapi.exceptions;

public class InvalidBookingOperation extends RuntimeException {
    public InvalidBookingOperation(String message){
        super(message);
    }

}