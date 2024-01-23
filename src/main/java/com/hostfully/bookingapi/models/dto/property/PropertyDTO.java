package com.hostfully.bookingapi.models.dto.property;

import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyDTO extends BaseDTO {
    public UUID id;
    public String name;
    public String address;

    public Property toEntity(){
        Property entity = new Property();

        entity.id = id;
        entity.name = name;
        entity.address = address;

        if (entity.id == null) {
            // If id is null, it's a new entity
            entity.createdDateTime = LocalDateTime.now(ZoneOffset.UTC);
        } else {
            // If id is not null, it's an existing entity being updated
            entity.updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
        }

        return entity;
    }
}
