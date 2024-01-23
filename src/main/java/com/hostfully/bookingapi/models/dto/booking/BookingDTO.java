package com.hostfully.bookingapi.models.dto.booking;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.helpers.ValidationHelpers;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class BookingDTO extends BaseDTO {
    public UUID id;
    public UUID guestId;
    public BookingStatus status;
    public UUID propertyId;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;

    public Booking toEntity(){
        Booking entity = new Booking();

        entity.id = id;
        entity.guestId = guestId;
        entity.startDateTime = startDateTime;
        entity.endDateTime = endDateTime;

        if (entity.id == null) {
            // If id is null or invalid, it's a new entity
            entity.createdDateTime = LocalDateTime.now(ZoneOffset.UTC);
        } else {
            // If id is not null, it's an existing entity being updated
            entity.updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
        }

        return entity;
    }
}
