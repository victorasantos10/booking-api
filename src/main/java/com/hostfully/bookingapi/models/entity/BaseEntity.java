package com.hostfully.bookingapi.models.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseEntity {
    public LocalDateTime createdDateTime;
    public LocalDateTime updatedDateTime;
}
