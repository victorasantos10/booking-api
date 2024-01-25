package com.hostfully.bookingapi.models.dto;

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
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public Property toEntityUpdate(Property entity){
        entity.name = name != null ? name : entity.name;
        entity.address = address != null ? address : entity.address;
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
