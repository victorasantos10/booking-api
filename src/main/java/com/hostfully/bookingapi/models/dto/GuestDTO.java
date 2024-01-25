package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class GuestDTO extends BaseDTO {
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
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
