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
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId", referencedColumnName = "id")
    public Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyTeamMemberId", referencedColumnName = "id")
    public PropertyTeamMember propertyTeamMember;

    @Column(name = "propertyId", updatable = false, insertable=false)
    public UUID propertyTeamMemberId;

    public LocalDateTime startDateTime;

    public String reason;

    public boolean isActive = true;

    public LocalDateTime endDateTime;

    public BlockDTO toDTO(){
        BlockDTO dto = new BlockDTO();

        dto.id = id;
        dto.propertyId = propertyId;
        dto.propertyTeamMemberId = propertyTeamMemberId;
        dto.startDateTime = startDateTime;
        dto.reason = reason;
        dto.endDateTime = endDateTime;
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }

}
