package com.hostfully.bookingapi.models.dto;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import static com.hostfully.bookingapi.helpers.DateHelpers.truncateAndSetToUTC;

@Data
@EqualsAndHashCode(callSuper = true)
public class BlockDTO extends BaseDTO {
    private UUID id;
    @Schema(example = "91b23fb9-d079-40aa-84e5-4c438ce99411")
    @NotNull(message = "Field is mandatory")
    private UUID propertyId;
    @Schema(example = "Maintenance", maxLength = 255)
    @Size(max = 255, message = "Reason description too long")
    @NotBlank(message = "Field is mandatory")
    private String reason;
    @Schema(example = "2024-01-01T00:00")
    @FutureOrPresent(message = "startDateTime cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDateTime startDateTime;
    @Schema(example = "2024-01-02T12:00")
    @FutureOrPresent(message = "endDateTime cannot be in the past")
    @NotNull(message = "Field is mandatory")
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
