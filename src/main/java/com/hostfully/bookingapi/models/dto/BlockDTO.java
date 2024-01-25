package com.hostfully.bookingapi.models.dto;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlockDTO extends BaseDTO {
    private UUID id;
    private UUID propertyId;
    private String reason;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;


    public Block toEntity(Property property, PropertyTeamMember propertyTeamMember){
        Block entity = new Block();

        entity.setReason(getReason());
        entity.setProperty(property);
        entity.setPropertyTeamMember(propertyTeamMember);
        entity.setStartDateTime(getStartDateTime());
        entity.setEndDateTime(getEndDateTime());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public Block toEntityUpdate(Block entity){
        entity.setReason(getReason() != null ? getReason() : entity.getReason());
        entity.setStartDateTime(getStartDateTime() != null ? getStartDateTime() : entity.getStartDateTime());
        entity.setEndDateTime(getEndDateTime() != null ? getEndDateTime() : entity.getEndDateTime());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
