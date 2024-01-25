package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.block.BlockDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.services.BlockService;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/blocks")
public class BlockController {

    @Autowired
    BlockService blockService;
    @Autowired
    PropertyTeamMemberService propertyTeamMemberService;

    @GetMapping("{teamMemberUUID}/detail/{blockUUID}")
    public ResponseEntity<ApiResponseDTO<BlockDTO>> getBlockById(@PathVariable UUID teamMemberUUID, @PathVariable UUID blockUUID){
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        return ResponseEntity.ok(new ApiResponseDTO<>(blockService.getBlock(blockUUID)));
    }

    @PostMapping("{teamMemberUUID}")
    public ResponseEntity<ApiResponseDTO<UUID>> createBlock(@PathVariable UUID teamMemberUUID, @RequestBody BlockDTO blockDTO){
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        return ResponseEntity.ok(new ApiResponseDTO<>(blockService.createBlock(blockDTO)));
    }

    @PutMapping("{teamMemberUUID}")
    public ResponseEntity updateBlock(@PathVariable UUID teamMemberUUID, @RequestBody BlockDTO blockDTO){
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        blockService.updateBlock(blockDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{teamMemberUUID}/{blockUUID}")
    public ResponseEntity deleteBlock(@PathVariable UUID teamMemberUUID, @PathVariable UUID blockUUID) {
        propertyTeamMemberService.validateTeamMember(teamMemberUUID);
        blockService.deleteBlock(blockUUID);
        return ResponseEntity.ok().build();
    }
}
