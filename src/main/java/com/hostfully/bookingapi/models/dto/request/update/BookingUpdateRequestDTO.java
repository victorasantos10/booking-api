package com.hostfully.bookingapi.models.dto.request.update;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.entity.Booking;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Data
public class BookingUpdateRequestDTO {
    @NotNull(message = "Field is mandatory")
    private UUID id;
    private BookingStatus status;
    @FutureOrPresent(message = "startDate cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDate startDate;
    @FutureOrPresent(message = "endDate cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDate endDate;
    @Min(value = 1, message = "It is required to have at least one adult")
    private Integer adults;
    @Max(value = 99, message = "Value too long")
    private Integer children;

    public Booking toEntityUpdate(Booking entity){
        entity.setStatus(getStatus() != null ? getStatus().getValue() : entity.getStatus());
        entity.setAdults(getAdults() != null ? getAdults() : entity.getAdults());
        entity.setChildren(getChildren() != null ? getChildren() : entity.getChildren());
        entity.setStartDate(getStartDate() != null ? getStartDate() : entity.getStartDate());
        entity.setEndDate(getEndDate() != null ? getEndDate() : entity.getEndDate());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
