package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.helpers.ValidationHelpers;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.models.entity.Property;
import io.swagger.v3.oas.annotations.media.Schema;
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
    public Integer adults;
    public Integer children;

    public Booking toEntity(Property property, Guest guest){
        Booking entity = new Booking();

        entity.id = id;
        entity.setProperty(property);
        entity.setGuest(guest);
        entity.status = status.getValue();
        entity.adults = adults;
        entity.children = children;
        entity.startDateTime = startDateTime;
        entity.endDateTime = endDateTime;

        if (entity.id == null) {
            // If id is null, it's a new entity
            entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        } else {
            // If id is not null, it's an existing entity being updated
            entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        }

        return entity;
    }
}
