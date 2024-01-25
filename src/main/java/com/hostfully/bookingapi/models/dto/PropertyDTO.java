package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyDTO extends BaseDTO {
    private UUID id;
    @NotBlank(message = "Name is mandatory")
    private String name;
    private String address;

    public Property toEntity(){
        Property entity = new Property();

        entity.setName(getName());
        entity.setAddress(getAddress());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public Property toEntityUpdate(Property entity){
        entity.setName(getName() != null ? getName() : entity.getName());
        entity.setAddress(getAddress() != null ? getAddress() : entity.getAddress());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
