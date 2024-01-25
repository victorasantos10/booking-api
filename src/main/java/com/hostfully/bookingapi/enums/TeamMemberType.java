package com.hostfully.bookingapi.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum TeamMemberType {
    MANAGER(1),
    OWNER(2);

    private Integer value;

    public static TeamMemberType valueOf(int value) {
        return Arrays.stream(values())
                .filter(item -> item.value == value)
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }
}
