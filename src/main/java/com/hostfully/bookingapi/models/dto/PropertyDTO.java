package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyDTO extends BaseDTO {
    private UUID id;

    @Size(min = 1, message = "Name too short")
    @Size(max = 255, message = "Name too long")
    @NotBlank(message = "Field is mandatory")
    private String name;

    @NotBlank(message = "Field is mandatory")
    @Size(min = 1, message = "Address too short")
    @Size(max = 255, message = "Address too long")
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
