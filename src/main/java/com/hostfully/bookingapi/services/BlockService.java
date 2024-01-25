package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.block.BlockDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.repositories.BlockRepository;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    PropertyRepository propertyRepository;

    public BlockDTO getBlock(UUID blockUUID){
        Block blockEntity = blockRepository.findById(blockUUID).orElseThrow(() -> new EntityNotFoundException("Block not found"));

        return blockEntity.toDTO();
    }

    public void updateBlock(BlockDTO dto){
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        blockRepository.save(dto.toEntity(property));
    }

    public UUID createBlock(BlockDTO dto){
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        Block savedEntity = blockRepository.save(dto.toEntity(property));
        return savedEntity.id;
    }

    public void deleteBlock(UUID blockUUID){
        blockRepository.deleteById(blockUUID);
    }
}
