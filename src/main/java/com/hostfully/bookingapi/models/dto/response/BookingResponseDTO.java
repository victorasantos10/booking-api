package com.hostfully.bookingapi.models.dto.response;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class BookingResponseDTO extends BaseResponseDTO {
    private UUID id;
    private UUID guestId;
    private BookingStatus status;
    private UUID propertyId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer adults;
    private Integer children;
}