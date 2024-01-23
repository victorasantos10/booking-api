package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.BookingStatus;
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
    @JoinColumn(name = "guestId")
    public Guest guest;

    @Column(name = "guestId", updatable = false, insertable=false)
    public UUID guestId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Property")
    @JoinColumn(name = "propertyId")
    public Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyId;

    public BookingStatus status;

    public LocalDateTime startDateTime;

    public LocalDateTime endDateTime;
}
