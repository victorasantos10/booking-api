package com.hostfully.bookingapi.models.dto.request.update;

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
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Data
public class BlockUpdateRequestDTO {
    @NotNull(message = "Field is mandatory")
    private UUID id;
    @Schema(example = "Maintenance", maxLength = 255)
    @Size(max = 255, message = "Reason description too long")
    private String reason;
    @Schema(example = "2024-01-01T00:00")
    @FutureOrPresent(message = "startDate cannot be in the past")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Field is mandatory")
    private LocalDate startDate;
    @Schema(example = "2024-01-02T12:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent(message = "endDate cannot be in the past")
    @NotNull(message = "Field is mandatory")
    private LocalDate endDate;


    public Block toEntityUpdate(Block entity){
        entity.setReason(getReason() != null ? getReason() : entity.getReason());
        entity.setStartDate(getStartDate() != null ? getStartDate() : entity.getStartDate());
        entity.setEndDate(getEndDate() != null ? getEndDate() : entity.getEndDate());
        entity.setUpdatedDateTime(LocalDateTime.now(ZoneOffset.UTC));

        return entity;
    }
}
