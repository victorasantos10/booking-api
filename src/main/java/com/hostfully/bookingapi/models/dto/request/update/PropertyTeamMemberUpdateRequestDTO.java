package com.hostfully.bookingapi.models.dto.request.update;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyTeamMemberUpdateRequestDTO {
    @Size(min = 1, message = "Name too short")
    @Size(max = 255, message = "Name too long")
    public String name;
    @NotNull(message = "Field is mandatory")
    public UUID id;
    public TeamMemberType type;

    public PropertyTeamMember toEntityUpdate(PropertyTeamMember entity) {
        entity.setName(getName() != null ? getName() : entity.getName());
        entity.setType(getType().getValue() != null ? getType().getValue() : entity.getType());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
