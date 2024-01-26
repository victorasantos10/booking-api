package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.request.create.BlockRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.BlockUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.BlockResponseDTO;
import com.hostfully.bookingapi.services.BlockService;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hostfully.bookingapi.helpers.ValidationHelpers.validateDateRange;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
})
@Tag(name = "Block API", description = "Creates a block for a given property. A block represents a specified amount of time that the property will remain unavailable. Note: If a block is created or updated targeting a date that has active bookings, all bookings are automatically cancelled")
@RestController
@RequestMapping(value = "/api/blocks")
public class BlockController {

    @Autowired
    BlockService blockService;
    @Autowired
    PropertyTeamMemberService propertyTeamMemberService;

    @Operation(summary = "Get a block by ID", description = "Note: Added teamMemberUUID on path to make endpoint available to be called only by team members (owners or managers).")
    @GetMapping("{teamMemberUUID}/detail/{blockUUID}")
    public ResponseEntity<ApiResponseDTO<BlockResponseDTO>> getBlockById(@PathVariable UUID teamMemberUUID, @PathVariable UUID blockUUID){
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        return ResponseEntity.ok(new ApiResponseDTO<>(blockService.getBlock(blockUUID)));
    }

    @Operation(summary = "Create a new block for a property", description = "Note: Added teamMemberUUID on path to make endpoint available to be called only by team members (owners or managers).")
    @PostMapping("{teamMemberUUID}")
    public ResponseEntity<ApiResponseDTO<UUID>> createBlock(@PathVariable UUID teamMemberUUID, @RequestBody BlockRequestDTO blockDTO){
        validateDateRange(blockDTO.getStartDate(), blockDTO.getEndDate());
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        return ResponseEntity.ok(new ApiResponseDTO<>(blockService.createBlock(teamMemberUUID, blockDTO)));
    }

    @Operation(summary = "Update property block", description = "Note: Added teamMemberUUID on path to make endpoint available to be called only by team members (owners or managers).")
    @PutMapping("{teamMemberUUID}")
    public ResponseEntity updateBlock(@PathVariable UUID teamMemberUUID, @RequestBody @Valid BlockUpdateRequestDTO blockDTO){
        validateDateRange(blockDTO.getStartDate(), blockDTO.getEndDate());
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        blockService.updateBlock(blockDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a property block", description = "Note: Added teamMemberUUID on path to make endpoint available to be called only by team members (owners or managers).")
    @DeleteMapping("{teamMemberUUID}/{blockUUID}")
    public ResponseEntity deleteBlock(@PathVariable UUID teamMemberUUID, @PathVariable UUID blockUUID) {
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        blockService.deleteBlock(blockUUID);
        return ResponseEntity.ok().build();
    }
}
