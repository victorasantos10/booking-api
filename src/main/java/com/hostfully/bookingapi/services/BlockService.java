package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.block.BlockDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.repositories.BlockRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    public BlockDTO getBlock(UUID blockUUID){
        Block blockEntity = blockRepository.findById(blockUUID).orElseThrow(() -> new EntityNotFoundException("Block not found"));

        return blockEntity.toDTO();
    }

    public void updateBlock(BlockDTO block){
        blockRepository.save(block.toEntity());
    }

    public UUID createBlock(BlockDTO block){
        Block savedEntity = blockRepository.save(block.toEntity());

        return savedEntity.id;
    }

    public void deleteBlock(UUID blockUUID){
        blockRepository.deleteById(blockUUID);
    }
}
