package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.propertyteammember.PropertyTeamMemberDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class PropertyTeamMember extends BaseEntity {
    @Id
    public UUID id;
    public String name;
    public TeamMemberType type;

    public PropertyTeamMemberDTO toDTO(){
        PropertyTeamMemberDTO dto = new PropertyTeamMemberDTO();

        dto.id = id;
        dto.name = name;
        dto.type = type;

        return dto;
    }
}
