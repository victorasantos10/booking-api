package com.hostfully.bookingapi.models.dto.request.create;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyTeamMemberRequestDTO {
    @Size(min = 1, message = "Name too short")
    @Size(max = 255, message = "Name too long")
    @NotBlank(message = "Field is mandatory")
    public String name;
    @NotNull(message = "Field is mandatory")
    @Schema(example = "91b23fb9-d079-40aa-84e5-4c438ce99411")
    public UUID propertyId;
    @NotNull(message = "Field is mandatory")
    public TeamMemberType type;

    public PropertyTeamMember toEntity(Property property){
        PropertyTeamMember entity = new PropertyTeamMember();

        entity.setProperty(property);
        entity.setName(getName());
        entity.setType(getType().getValue());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
