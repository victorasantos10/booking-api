package com.hostfully.bookingapi.models.dto.response;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;


@Data
public class BlockResponseDTO extends BaseResponseDTO {
    private UUID id;
    private UUID propertyId;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
}
