package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.dto.response.BookingResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Booking extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guestId", referencedColumnName = "id")
    private Guest guest;

    @Column(name = "guestId", updatable = false, insertable=false)
    private UUID guestId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId", referencedColumnName = "id")
    private Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    private UUID propertyId;

    private Integer adults;
    private Integer children;

    private Integer status;

    private LocalDate startDate;

    private LocalDate endDate;

    public BookingResponseDTO toDTO(){
        BookingResponseDTO dto = new BookingResponseDTO();

        dto.setId(getId());
        dto.setStartDate(getStartDate());
        dto.setEndDate(getEndDate());
        dto.setGuestId(getGuest().getId());
        dto.setPropertyId(getProperty().getId());
        dto.setStatus(BookingStatus.valueOf(status));
        dto.setAdults(getAdults());
        dto.setChildren(getChildren());
        dto.setCreatedDateTime(getCreatedDateTime());
        dto.setUpdatedDateTime(getUpdatedDateTime());

        return dto;
    }

}
