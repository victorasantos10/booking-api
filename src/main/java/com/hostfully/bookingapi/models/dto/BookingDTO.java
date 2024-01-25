package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.helpers.ValidationHelpers;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.models.entity.Property;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
public class BookingDTO extends BaseDTO {
    private UUID id;
    private UUID guestId;
    private BookingStatus status;
    private UUID propertyId;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    @Min(value = 1, message = "Age should not be less than 15")
    private Integer adults;
    private Integer children;

    public Booking toEntity(Property property, Guest guest){
        Booking entity = new Booking();

        entity.setProperty(property);
        entity.setGuest(guest);
        entity.setStatus(getStatus().getValue());
        entity.setAdults(getAdults());
        entity.setChildren(getChildren());
        entity.setStartDateTime(getStartDateTime());
        entity.setEndDateTime(getEndDateTime());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public Booking toEntityUpdate(Booking entity){
        entity.setStatus(getStatus() != null ? getStatus().getValue() : entity.getStatus());
        entity.setAdults(getAdults() != null ? getAdults() : entity.getAdults());
        entity.setChildren(getChildren() != null ? getChildren() : entity.getChildren());
        entity.setStartDateTime(getStartDateTime() != null ? getStartDateTime() : entity.getStartDateTime());
        entity.setEndDateTime(getEndDateTime() != null ? getEndDateTime() : entity.getEndDateTime());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
