package com.hostfully.bookingapi.enums;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TeamMemberType {
    MANAGER(1),
    OWNER(2);

    private Integer value;
}
