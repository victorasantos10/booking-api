package com.hostfully.bookingapi.models.dto.request.create;

import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyRequestDTO {

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
}
