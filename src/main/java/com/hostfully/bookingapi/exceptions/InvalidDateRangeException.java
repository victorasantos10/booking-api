package com.hostfully.bookingapi.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class InvalidDateRangeException extends RuntimeException {

    public InvalidDateRangeException(String message){
        super(message);
    }
}
