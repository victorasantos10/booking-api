package com.hostfully.bookingapi.helpers;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;

public class DateHelpers {
    public static LocalDateTime truncateAndSetToUTC(LocalDateTime localDateTime){
        return localDateTime.truncatedTo(ChronoUnit.MINUTES).atZone(ZoneOffset.UTC).toLocalDateTime();
    }
}
