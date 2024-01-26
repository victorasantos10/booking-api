package com.hostfully.bookingapi.models.dto.response;

import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class GuestResponseDTO extends BaseResponseDTO {
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;
}
