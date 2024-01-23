package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.guest.GuestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Guest extends BaseEntity {
    @Id
    public UUID id;
    public String name;
    public LocalDate dateOfBirth;
    public String email;
    public String phone;
    public Integer adults;
    public Integer children;

    public GuestDTO toDTO(){
        GuestDTO dto = new GuestDTO();
        dto.id = id;
        dto.name = name;
        dto.dateOfBirth = dateOfBirth;
        dto.email = email;
        dto.phone = phone;
        dto.adults = adults;
        dto.children = children;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }
}
