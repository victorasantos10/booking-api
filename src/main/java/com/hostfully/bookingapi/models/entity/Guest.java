package com.hostfully.bookingapi.models.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
public class Guest {
    @Id
    private UUID id;
    private String name;
    private LocalDate dateOfBirth;
    private String email;
}
