package com.hostfully.bookingapi.models.dto.request.create;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.models.entity.Property;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Data
public class BookingRequestDTO {
    @Schema(example = "178147cd-b3e6-4e64-91e2-af16bd22c8f0")
    @NotNull(message = "Field is mandatory")
    private UUID guestId;
    @NotNull(message = "Field is mandatory")
    private BookingStatus status;
    @Schema(example = "91b23fb9-d079-40aa-84e5-4c438ce99411")
    @NotNull(message = "Field is mandatory")
    private UUID propertyId;
    @Schema(example = "2024-12-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent(message = "startDate cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDate startDate;
    @Schema(example = "2024-12-02")
    @FutureOrPresent(message = "endDate cannot be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Field is mandatory")
    private LocalDate endDate;
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
        entity.setStartDate(getStartDate());
        entity.setEndDate(getEndDate());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

}
