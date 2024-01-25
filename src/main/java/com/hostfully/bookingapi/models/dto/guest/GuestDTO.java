package com.hostfully.bookingapi.models.dto.guest;

import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class GuestDTO extends BaseDTO {
    public UUID id;
    public String name;
    public LocalDate dateOfBirth;
    public String email;
    public String phone;

    public Guest toEntity(){

        Guest entity = new Guest();

        entity.id = id;
        entity.name = name;
        entity.dateOfBirth = dateOfBirth;
        entity.email = email;
        entity.phone = phone;


        if (entity.id == null) {
            // If id is null or invalid, it's a new entity
            entity.createdDateTime = LocalDateTime.now(ZoneOffset.UTC);
        } else {
            // If id is not null, it's an existing entity being updated
            entity.updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
        }


        return entity;
    }
}
