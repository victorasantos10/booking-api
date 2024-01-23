package com.hostfully.bookingapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookingStatus {
    ACTIVE(1),
    CANCELLED(2),
    BLOCKED(3);

    private Integer value;
}
