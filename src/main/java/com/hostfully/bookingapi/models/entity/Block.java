package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.BlockDTO;
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
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "propertyId", referencedColumnName = "id")
    private Property property;

    @Column(name = "propertyId", updatable = false, insertable=false)
    private UUID propertyId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdByPropertyTeamMemberId", referencedColumnName = "id")
    private PropertyTeamMember propertyTeamMember;

    @Column(name = "createdByPropertyTeamMemberId", updatable = false, insertable=false)
    private UUID propertyTeamMemberId;

    private LocalDateTime startDateTime;

    private String reason;

    private boolean isActive = true;

    private LocalDateTime endDateTime;

    public BlockDTO toDTO(){
        BlockDTO dto = new BlockDTO();

        dto.setId(getId());
        dto.setPropertyId(getPropertyId());
        dto.setStartDateTime(getStartDateTime());
        dto.setReason(getReason());
        dto.setEndDateTime(getEndDateTime());
        dto.createdDateTime = createdDateTime;
        dto.updatedDateTime = updatedDateTime;

        return dto;
    }

}
