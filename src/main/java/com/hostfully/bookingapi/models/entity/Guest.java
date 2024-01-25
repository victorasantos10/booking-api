package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.GuestDTO;
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
    public UUID id;
    public String name;
    public LocalDate dateOfBirth;
    public String email;
    public String phone;

    public GuestDTO toDTO(){
        GuestDTO dto = new GuestDTO();
        dto.id = id;
        dto.name = name;
        dto.dateOfBirth = dateOfBirth;
        dto.email = email;
        dto.phone = phone;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }
}
