package com.hostfully.bookingapi.models.dto.booking;

import com.hostfully.bookingapi.models.entity.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateBookingDTO {
    private UUID guestId;
    private UUID propertyId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Booking toEntity(){
        Booking entity = new Booking();

        entity.guestId = guestId;
        entity.startDateTime = startDateTime;
        entity.endDateTime = endDateTime;

        return entity;
    }
}
