package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.guest.GuestDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Guest extends BaseEntity {
    @Id
    public UUID id = UUID.randomUUID();;
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
