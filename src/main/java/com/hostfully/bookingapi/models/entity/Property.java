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
    @JoinTable(name = "Property")
    @JoinColumn(name = "id")
    private Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    private UUID propertyId;

    private String name;
    private String address;
}
