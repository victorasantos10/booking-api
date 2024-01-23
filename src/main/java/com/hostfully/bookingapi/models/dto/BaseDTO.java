package com.hostfully.bookingapi.models.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {

    public LocalDateTime createdDateTime;
    public LocalDateTime updatedDateTime;
}
