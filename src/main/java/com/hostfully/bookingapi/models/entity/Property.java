package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.property.PropertyDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Property extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

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
