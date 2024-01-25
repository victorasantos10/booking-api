package com.hostfully.bookingapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Getter
@AllArgsConstructor
public enum BookingStatus {
    ACTIVE(1),
    CANCELLED(2),
    BLOCKED(3);

    private Integer value;

    public static BookingStatus valueOf(int value) {
        return Arrays.stream(values())
                .filter(item -> item.value == value)
                .findFirst().orElseThrow(() -> new IllegalArgumentException("An error occurred"));
    }
}
