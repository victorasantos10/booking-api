package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.block.BlockDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.services.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/blockings")
public class BlockController {

    @Autowired
    BlockService blockService;

    @GetMapping("{blockUUID}")
    public ResponseEntity<ApiResponseDTO<BlockDTO>> getBlockById(@PathVariable UUID blockUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(blockService.getBlock(blockUUID)));
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> createBlock(@RequestBody BlockDTO blockDTO){
        return ResponseEntity.ok(new ApiResponseDTO<>(blockService.createBlock(blockDTO)));
    }

    @PutMapping()
    public ResponseEntity updateBlock(@RequestBody BlockDTO blockDTO){
        blockService.updateBlock(blockDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{blockUUID}")
    public ResponseEntity deleteBlock(@PathVariable UUID blockUUID) {
        blockService.deleteBlock(blockUUID);
        return ResponseEntity.ok().build();
    }
}
