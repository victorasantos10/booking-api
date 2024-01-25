package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.PropertyTeamMemberDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@DynamicUpdate
@Entity
@Data
public class PropertyTeamMember extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId")
    private Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    private UUID propertyId;

    private String name;
    private Integer type;

    public PropertyTeamMemberDTO toDTO(){
        PropertyTeamMemberDTO dto = new PropertyTeamMemberDTO();

        dto.setId(getId());
        dto.setPropertyId(getProperty().getId());
        dto.setName(getName());
        dto.setType(TeamMemberType.valueOf(type));

        return dto;
    }
}
