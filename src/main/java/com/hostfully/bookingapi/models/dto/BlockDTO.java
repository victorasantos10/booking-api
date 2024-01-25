package com.hostfully.bookingapi.models.dto;
import com.hostfully.bookingapi.models.dto.BaseDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static com.hostfully.bookingapi.helpers.DateHelpers.truncateAndSetToUTC;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlockDTO extends BaseDTO {
    private UUID id;
    @Schema(example = "91b23fb9-d079-40aa-84e5-4c438ce99411")
    private UUID propertyId;
    @Schema(example = "Maintenance", maxLength = 255)
    @Size(max = 255, message = "Reason description too long")
    private String reason;
    @Schema(example = "2024-01-01T00:00")
    @PastOrPresent(message = "startDateTime should be lower or equal than today")
    private LocalDateTime startDateTime;
    @Schema(example = "2024-01-02T12:00")
    @FutureOrPresent(message = "endDateTime should be higher or equal than today")
    private LocalDateTime endDateTime;


    public Block toEntity(Property property, PropertyTeamMember propertyTeamMember){
        Block entity = new Block();

        entity.setReason(getReason());
        entity.setProperty(property);
        entity.setPropertyTeamMember(propertyTeamMember);
        entity.setStartDateTime(truncateAndSetToUTC(getStartDateTime()));
        entity.setEndDateTime(truncateAndSetToUTC(getEndDateTime()));
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }

    public Block toEntityUpdate(Block entity){
        entity.setReason(getReason() != null ? getReason() : entity.getReason());
        entity.setStartDateTime(getStartDateTime() != null ? truncateAndSetToUTC(getStartDateTime()) : truncateAndSetToUTC(entity.getStartDateTime()));
        entity.setEndDateTime(getEndDateTime() != null ? truncateAndSetToUTC(getEndDateTime()) : truncateAndSetToUTC(entity.getEndDateTime()));
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
