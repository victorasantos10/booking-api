package com.hostfully.bookingapi.models.dto.block;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Property;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlockDTO extends BaseDTO {
    public UUID id;
    public UUID propertyId;
    public String reason;
    public LocalDateTime startDateTime;
    public LocalDateTime endDateTime;


    public Block toEntity(Property property){
        Block entity = new Block();

        entity.reason = reason;
        entity.id = id;
        entity.property = property;
        entity.startDateTime = startDateTime;
        entity.endDateTime = endDateTime;

        if (entity.id == null) {
            // If id is null or invalid, it's a new entity
            entity.createdDateTime = LocalDateTime.now(ZoneOffset.UTC);
        } else {
            // If id is not null, it's an existing entity being updated
            entity.updatedDateTime = LocalDateTime.now(ZoneOffset.UTC);
        }

        return entity;
    }
}
