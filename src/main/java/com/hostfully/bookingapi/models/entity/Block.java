package com.hostfully.bookingapi.models.entity;

import com.hostfully.bookingapi.models.dto.response.BlockResponseDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    private LocalDate startDate;

    private String reason;

    private boolean isActive = true;

    private LocalDate endDate;

    public BlockResponseDTO toDTO(){
        BlockResponseDTO dto = new BlockResponseDTO();

        dto.setId(getId());
        dto.setPropertyId(getPropertyId());
        dto.setStartDate(getStartDate());
        dto.setReason(getReason());
        dto.setEndDate(getEndDate());
        dto.setCreatedDateTime(getCreatedDateTime());
        dto.setUpdatedDateTime(getUpdatedDateTime());

        return dto;
    }

}
