package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.dto.booking.BookingDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "guestId", referencedColumnName = "id")
    public Guest guest;

    @Column(name = "guestId", updatable = false, insertable=false)
    public UUID guestId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId", referencedColumnName = "id")
    public Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyId;

    public Integer adults;
    public Integer children;

    public Integer status;

    public LocalDateTime startDateTime;

    public LocalDateTime endDateTime;

    public BookingDTO toDTO(){
        BookingDTO dto = new BookingDTO();

        dto.id = id;
        dto.startDateTime = startDateTime;
        dto.endDateTime = endDateTime;
        dto.guestId = guest.getId();
        dto.propertyId = property.getId();
        dto.status = BookingStatus.valueOf(status);
        dto.adults = adults;
        dto.children = children;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }

}
