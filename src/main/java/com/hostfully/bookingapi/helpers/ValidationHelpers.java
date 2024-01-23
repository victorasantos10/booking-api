package com.hostfully.bookingapi.helpers;

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
}
