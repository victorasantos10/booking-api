package com.hostfully.bookingapi.helpers;

import com.hostfully.bookingapi.exceptions.InvalidDateRangeException;

import java.time.LocalDateTime;
import java.util.UUID;

public class ValidationHelpers {

    public static boolean isUUIDValid(String input){
        try {
            UUID.fromString(input);

            return true;
        }
        catch(IllegalArgumentException ex){
            return false;
        }
    }

    public static void validateDateRange(LocalDateTime startDateTime, LocalDateTime endDateTime){
        if(startDateTime.isAfter(endDateTime) || endDateTime.isBefore(startDateTime)){
            throw new InvalidDateRangeException();
        }
    }
}
