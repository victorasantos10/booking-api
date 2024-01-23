package com.hostfully.bookingapi.models.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Booking {
    @Setter(AccessLevel.NONE)
    @Id
    public UUID id = UUID.randomUUID();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Guest")
    @JoinColumn(name = "id")
    public Guest guest;

    @Column(name = "guestId", updatable = false, insertable=false)
    public UUID guestId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Property")
    @JoinColumn(name = "id")
    public Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyId;

    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;
}
