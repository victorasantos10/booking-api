package com.hostfully.bookingapi.models.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseResponseDTO {
    protected LocalDateTime createdDateTime;
    protected LocalDateTime updatedDateTime;
}
