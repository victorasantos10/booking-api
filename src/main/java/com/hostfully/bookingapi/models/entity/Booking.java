package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.dto.booking.BookingDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
public class Booking extends BaseEntity {
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

    public BookingDTO toDTO(){
        BookingDTO dto = new BookingDTO();

        dto.id = id;
        dto.startDateTime = startDateTime;
        dto.endDateTime = endDateTime;
        dto.guestId = guestId;
        dto.propertyId = propertyId;
        dto.status = status;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }
}
