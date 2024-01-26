package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.response.PropertyResponseDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicUpdate;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@DynamicUpdate
@Entity
public class Property extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String name;
    public String address;

    public PropertyResponseDTO toDTO(){
        PropertyResponseDTO dto = new PropertyResponseDTO();

        dto.setId(getId());
        dto.setName(getName());
        dto.setAddress(getAddress());
        dto.setCreatedDateTime(getCreatedDateTime());
        dto.setUpdatedDateTime(getUpdatedDateTime());

        return dto;
    }
}
