package com.hostfully.bookingapi.models.dto.request.create;

import com.hostfully.bookingapi.models.dto.BaseResponseDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Data
public class BlockRequestDTO {
    @Schema(example = "91b23fb9-d079-40aa-84e5-4c438ce99411")
    @NotNull(message = "Field is mandatory")
    private UUID propertyId;
    @Schema(example = "Maintenance", maxLength = 255)
    @Size(max = 255, message = "Reason description too long")
    @NotBlank(message = "Field is mandatory")
    private String reason;
    @Schema(example = "2024-01-01T00:00")
    @FutureOrPresent(message = "startDate cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDate startDate;
    @Schema(example = "2024-01-02T12:00")
    @FutureOrPresent(message = "endDate cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDate endDate;

    public Block toEntity(Property property, PropertyTeamMember propertyTeamMember){
        Block entity = new Block();

        entity.setReason(getReason());
        entity.setProperty(property);
        entity.setPropertyTeamMember(propertyTeamMember);
        entity.setStartDate(getStartDate());
        entity.setEndDate(getEndDate());
        entity.setCreatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
