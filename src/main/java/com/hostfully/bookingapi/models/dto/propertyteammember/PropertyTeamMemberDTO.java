package com.hostfully.bookingapi.models.dto.propertyteammember;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyTeamMemberDTO extends BaseDTO {
    public String name;
    public UUID id;
    public TeamMemberType type;

    public PropertyTeamMember toEntity(){
        PropertyTeamMember entity = new PropertyTeamMember();

        entity.id = id;
        entity.name = name;
        entity.type = type;

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
