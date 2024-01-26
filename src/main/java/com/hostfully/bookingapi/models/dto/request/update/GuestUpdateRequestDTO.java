package com.hostfully.bookingapi.models.dto.request.update;

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
public class GuestUpdateRequestDTO {
    @Schema(example = "178147cd-b3e6-4e64-91e2-af16bd22c8f0")
    @NotNull(message = "Field is mandatory")
    private UUID id;

    @Schema(example = "John Doe", maxLength = 255)
    @Size(min = 1, message = "Name too short")
    @Size(max = 255, message = "Name too long")
    private String name;

    @Schema(example = "1980-01-01")
    @Past(message = "Only past dates are allowed")
    private LocalDate dateOfBirth;

    @Email(message="Please provide a valid email address")
    @Schema(example = "johndoe@contoso.com", maxLength = 255)
    private String email;

    @Pattern(regexp = "\\+[0-9]+", message = "Number should be formatted as +<phone_number>")
    @Schema(example = "+123456789", maxLength = 255)
    private String phone;

    public Guest toEntityUpdate(Guest entity){
        entity.setName(getName() != null ? getName() : entity.getName());
        entity.setDateOfBirth(getDateOfBirth() != null ? getDateOfBirth() : entity.getDateOfBirth());
        entity.setEmail(getEmail() != null ? getEmail() : entity.getEmail());
        entity.setPhone(getPhone() != null ? getPhone() : entity.getPhone());

        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        return entity;
    }
}
