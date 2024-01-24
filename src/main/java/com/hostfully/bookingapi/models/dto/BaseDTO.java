package com.hostfully.bookingapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseDTO {

    @Schema(hidden = true)
    public LocalDateTime createdDateTime;
    @Schema(hidden = true)
    public LocalDateTime updatedDateTime;
}
