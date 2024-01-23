package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.property.PropertyDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Property extends BaseEntity {
    @Id
    public UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PropertyTeamMember")
    @JoinColumn(name = "propertyTeamMemberId")
    public PropertyTeamMember propertyTeamMember;

    @Column(name = "propertyTeamMemberId", updatable = false, insertable=false)
    public UUID propertyTeamMemberId;

    public String name;
    public String address;

    public PropertyDTO toDTO(){
        PropertyDTO dto = new PropertyDTO();

        dto.id = id;
        dto.name = name;
        dto.address = address;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }
}
