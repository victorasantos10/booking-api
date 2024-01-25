package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class GuestDTO extends BaseDTO {
    @Schema(example = "178147cd-b3e6-4e64-91e2-af16bd22c8f0")
    private UUID id;
    @Schema(example = "John Doe", maxLength = 255)
    private String name;
    @Schema(example = "1980-01-01")
    private LocalDate dateOfBirth;
    @Schema(example = "johndoe@contoso.com", maxLength = 255)
    private String email;
    @Schema(example = "+123456789", maxLength = 255)
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

    public Guest toEntityUpdate(Guest entity){
        entity.setName(getName() != null ? getName() : entity.getName());
        entity.setDateOfBirth(getDateOfBirth() != null ? getDateOfBirth() : entity.getDateOfBirth());
        entity.setEmail(getEmail() != null ? getEmail() : entity.getEmail());
        entity.setPhone(getPhone() != null ? getPhone() : entity.getPhone());

        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        return entity;
    }
}
