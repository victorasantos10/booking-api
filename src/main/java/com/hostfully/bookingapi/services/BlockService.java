package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.exceptions.ExistingBlockException;
import com.hostfully.bookingapi.models.dto.request.create.BlockRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.BlockUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.BlockResponseDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import com.hostfully.bookingapi.repositories.BlockRepository;
import com.hostfully.bookingapi.repositories.BookingRepository;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import com.hostfully.bookingapi.repositories.PropertyTeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BlockService {

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    PropertyTeamMemberRepository propertyTeamMemberRepository;

    @Autowired
    BookingRepository bookingRepository;

    public BlockResponseDTO getBlock(UUID blockUUID){
        Block blockEntity = blockRepository.findById(blockUUID).orElseThrow(() -> new EntityNotFoundException("Block not found"));

        return blockEntity.toDTO();
    }

    public void updateBlock(BlockUpdateRequestDTO dto){
        //If there are any bookings in that date, set them as blocked.
        Block blockEntity = blockRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Block not found"));

        ArrayList<Booking> activeBookings = bookingRepository.findActiveOrRebookedBookingsWithinDate(blockEntity.getPropertyId(), dto.getStartDate(), dto.getEndDate());
        bookingRepository.updateBookingsStatus(activeBookings.stream().map(Booking::getId).collect(Collectors.toCollection(ArrayList::new)), BookingStatus.BLOCKED.getValue());

        blockRepository.save(dto.toEntityUpdate(blockEntity));
    }

    public UUID createBlock(UUID teamMemberUUID, BlockRequestDTO dto){
        //Checking if property is valid.
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        PropertyTeamMember propertyTeamMember = propertyTeamMemberRepository.findById(teamMemberUUID).orElseThrow(() -> new EntityNotFoundException("Team member not found"));

        //if there are any blocks already with that start and end date, throw exception
        List<Block> overlappingBlocks = blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(dto.getPropertyId(), true, dto.getStartDate(), dto.getEndDate());

        if(!overlappingBlocks.isEmpty()){
            throw new ExistingBlockException("There is already a current block in conflict with those dates");
        }

        //If there are any bookings in that date, set them as blocked.
        ArrayList<Booking> activeBookings = bookingRepository.findActiveOrRebookedBookingsWithinDate(dto.getPropertyId(), dto.getStartDate(), dto.getEndDate());
        bookingRepository.updateBookingsStatus(activeBookings.stream().map(Booking::getId).collect(Collectors.toCollection(ArrayList::new)), BookingStatus.BLOCKED.getValue());

        Block savedEntity = blockRepository.save(dto.toEntity(property, propertyTeamMember));
        return savedEntity.getId();
    }

    public void deleteBlock(UUID blockUUID){
        blockRepository.deleteById(blockUUID);
    }
}
