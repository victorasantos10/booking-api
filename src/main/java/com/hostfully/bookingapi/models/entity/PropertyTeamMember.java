package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.propertyteammember.PropertyTeamMemberDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class PropertyTeamMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Property")
    @JoinColumn(name = "propertyId")
    public Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyId;

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
