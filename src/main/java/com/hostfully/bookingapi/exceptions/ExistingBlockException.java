package com.hostfully.bookingapi.exceptions;

public class ExistingBlockException extends RuntimeException {
    public ExistingBlockException(String message){
        super(message);
    }

}
