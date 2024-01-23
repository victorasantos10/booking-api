package com.hostfully.bookingapi.models.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Property {
    @Id
    private UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "PropertyTeamMember")
    @JoinColumn(name = "propertyTeamMemberId")
    private PropertyTeamMember propertyTeamMember;

    @Column(name = "propertyTeamMemberId", updatable = false, insertable=false)
    private UUID propertyTeamMemberId;

    private String name;
    private String address;
}
