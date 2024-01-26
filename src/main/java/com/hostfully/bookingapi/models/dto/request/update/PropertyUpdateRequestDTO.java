package com.hostfully.bookingapi.models.dto.request.update;

import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyUpdateRequestDTO {
    @NotNull(message = "Field is mandatory")
    private UUID id;

    @Size(min = 1, message = "Name too short")
    @Size(max = 255, message = "Name too long")
    private String name;

    @Size(min = 1, message = "Address too short")
    @Size(max = 255, message = "Address too long")
    private String address;

    public Property toEntityUpdate(Property entity){
        entity.setName(getName() != null ? getName() : entity.getName());
        entity.setAddress(getAddress() != null ? getAddress() : entity.getAddress());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
