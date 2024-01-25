package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class PropertyTeamMemberDTO extends BaseDTO {
    public String name;
    public UUID id;
    public UUID propertyId;
    public TeamMemberType type;

    public PropertyTeamMember toEntity(Property property){
        PropertyTeamMember entity = new PropertyTeamMember();

        entity.setProperty(property);
        entity.setName(getName());
        entity.setType(getType().getValue());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public PropertyTeamMember toEntityUpdate(PropertyTeamMember entity) {
        entity.setName(getName() != null ? getName() : entity.getName());
        entity.setType(getType().getValue() != null ? getType().getValue() : entity.getType());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}