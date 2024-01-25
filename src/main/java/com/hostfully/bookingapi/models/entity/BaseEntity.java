package com.hostfully.bookingapi.models.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@MappedSuperclass
public class BaseEntity {
    protected LocalDateTime createdDateTime;
    protected LocalDateTime updatedDateTime;
}
