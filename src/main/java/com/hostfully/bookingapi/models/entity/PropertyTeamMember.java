package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.TeamMemberType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
@Data
public class PropertyTeamMember {
    @Id
    private UUID id;
    private String name;
    private TeamMemberType type;
}
