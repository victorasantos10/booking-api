package com.hostfully.bookingapi.models.dto;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.helpers.ValidationHelpers;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.models.entity.Property;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static com.hostfully.bookingapi.helpers.DateHelpers.truncateAndSetToUTC;

@Data
public class BookingDTO extends BaseDTO {
    private UUID id;
    @NotNull(message = "Field is mandatory")
    private UUID guestId;
    @NotNull(message = "Field is mandatory")
    private BookingStatus status;
    @NotNull(message = "Field is mandatory")
    private UUID propertyId;
    @FutureOrPresent(message = "startDateTime cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDateTime startDateTime;
    @FutureOrPresent(message = "endDateTime cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDateTime endDateTime;
    @Min(value = 1, message = "It is required to have at least one adult")
    @NotNull(message = "Field is mandatory")
    private Integer adults;
    @Max(value = 99, message = "Value too long")
    private Integer children;

    public Booking toEntity(Property property, Guest guest){
        Booking entity = new Booking();

        entity.setProperty(property);
        entity.setGuest(guest);
        entity.setStatus(getStatus().getValue());
        entity.setAdults(getAdults());
        entity.setChildren(getChildren());
        entity.setStartDateTime(truncateAndSetToUTC(getStartDateTime()));
        entity.setEndDateTime(truncateAndSetToUTC(getEndDateTime()));
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public Booking toEntityUpdate(Booking entity){
        entity.setStatus(getStatus() != null ? getStatus().getValue() : entity.getStatus());
        entity.setAdults(getAdults() != null ? getAdults() : entity.getAdults());
        entity.setChildren(getChildren() != null ? getChildren() : entity.getChildren());
        entity.setStartDateTime(getStartDateTime() != null ? truncateAndSetToUTC(getStartDateTime()) : truncateAndSetToUTC(entity.getStartDateTime()));
        entity.setEndDateTime(getEndDateTime() != null ? truncateAndSetToUTC(getEndDateTime()) : truncateAndSetToUTC(entity.getEndDateTime()));
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
