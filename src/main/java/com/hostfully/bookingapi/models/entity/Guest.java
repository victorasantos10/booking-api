package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.response.GuestResponseDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Guest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String phone;

    public GuestResponseDTO toDTO(){
        GuestResponseDTO dto = new GuestResponseDTO();
        dto.setId(getId());
        dto.setName(getName());
        dto.setDateOfBirth(getDateOfBirth());
        dto.setEmail(getEmail());
        dto.setPhone(getPhone());
        dto.setCreatedDateTime(getCreatedDateTime());
        dto.setUpdatedDateTime(getUpdatedDateTime());

        return dto;
    }
}
