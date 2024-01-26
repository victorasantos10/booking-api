package com.hostfully.bookingapi.models.dto.request.create;

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
public class GuestRequestDTO {
    @Schema(example = "John Doe", maxLength = 255)
    @Size(min = 1, message = "Name too short")
    @Size(max = 255, message = "Name too long")
    @NotBlank(message = "Field is mandatory")
    private String name;

    @Schema(example = "1980-01-01")
    @Past(message = "Only past dates are allowed")
    @NotNull(message = "Field is mandatory")
    private LocalDate dateOfBirth;

    @Email(message="Please provide a valid email address")
    @Schema(example = "johndoe@contoso.com", maxLength = 255)
    @NotBlank(message = "Field is mandatory")
    private String email;

    @Pattern(regexp = "\\+[0-9]+", message = "Number should be formatted as +<phone_number>")
    @Schema(example = "+123456789", maxLength = 255)
    @NotBlank(message = "Field is mandatory")
    private String phone;

    public Guest toEntity(){
        Guest entity = new Guest();
        entity.setName(getName());
        entity.setDateOfBirth(getDateOfBirth());
        entity.setEmail(getEmail());
        entity.setPhone(getPhone());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        return entity;
    }
}
