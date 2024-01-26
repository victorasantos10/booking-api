package com.hostfully.bookingapi.helpers;

import com.hostfully.bookingapi.exceptions.DateTimeIntervalTooShortException;
import com.hostfully.bookingapi.exceptions.InvalidDateRangeException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

    public static void validateDateRange(LocalDate startDate, LocalDate endDate){
        if(startDate.isAfter(endDate) || endDate.isBefore(startDate)){
            throw new InvalidDateRangeException();
        }

        if(ChronoUnit.DAYS.between(startDate, endDate) < 1){
            throw new DateTimeIntervalTooShortException("Interval between start and end dates should be greater than or equal to 1 day");
        }
    }
}
