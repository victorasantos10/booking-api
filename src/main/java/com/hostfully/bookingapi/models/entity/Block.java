package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.block.BlockDTO;
import com.hostfully.bookingapi.models.dto.booking.BookingDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@Entity
public class Block extends BaseEntity {
    @Id
    public UUID id = UUID.randomUUID();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "Property")
    @JoinColumn(name = "propertyId")
    public Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyId;

    public LocalDateTime startDateTime;

    public String reason;

    public LocalDateTime endDateTime;

    public BlockDTO toDTO(){
        BlockDTO dto = new BlockDTO();

        dto.id = id;
        dto.propertyId = propertyId;
        dto.startDateTime = startDateTime;
        dto.reason = reason;
        dto.endDateTime = endDateTime;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }

}
