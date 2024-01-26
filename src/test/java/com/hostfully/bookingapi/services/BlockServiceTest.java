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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BlockServiceTest {

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private PropertyTeamMemberRepository propertyTeamMemberRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private BlockService blockService;

    private UUID blockUUID;
    private Block block;
    private UUID propertyUUID;
    private Property property;
    private UUID teamMemberUUID;
    private PropertyTeamMember propertyTeamMember;
    private BlockRequestDTO blockRequestDTO;
    private BlockUpdateRequestDTO blockUpdateRequestDTO;

    @BeforeEach
    public void setUp() {
        blockUUID = UUID.randomUUID();
        block = new Block();
        block.setId(blockUUID);

        propertyUUID = UUID.randomUUID();
        property = new Property();
        property.setId(propertyUUID);

        teamMemberUUID = UUID.randomUUID();
        propertyTeamMember = new PropertyTeamMember();
        propertyTeamMember.setId(teamMemberUUID);

        blockRequestDTO = new BlockRequestDTO();
        blockRequestDTO.setPropertyId(propertyUUID);
        blockRequestDTO.setStartDate(LocalDate.now().plusDays(1));
        blockRequestDTO.setEndDate(LocalDate.now().plusDays(2));

        blockUpdateRequestDTO = new BlockUpdateRequestDTO();
        blockUpdateRequestDTO.setId(blockUUID);
        blockUpdateRequestDTO.setStartDate(LocalDate.now().plusDays(1));
        blockUpdateRequestDTO.setEndDate(LocalDate.now().plusDays(2));
        lenient().when(blockRepository.findById(blockUUID)).thenReturn(Optional.of(block));
    }

    @Test
    public void testGetBlockWhenValidUUIDThenReturnBlockResponseDTO() {
        when(blockRepository.findById(blockUUID)).thenReturn(Optional.of(block));

        BlockResponseDTO result = blockService.getBlock(blockUUID);

        assertEquals(block.toDTO(), result);
    }

    @Test
    public void testGetBlockWhenInvalidUUIDThenThrowEntityNotFoundException() {
        UUID invalidUUID = UUID.randomUUID();
        when(blockRepository.findById(invalidUUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> blockService.getBlock(invalidUUID));
    }

    @Test
    public void testUpdateBlock() {

        blockService.updateBlock(blockUpdateRequestDTO);

        verify(bookingRepository, times(1)).updateBookingsStatus(any(ArrayList.class), eq(BookingStatus.BLOCKED.getValue()));
        verify(blockRepository, times(1)).save(any(Block.class));
    }

    @Test
    public void testCreateBlock() {
        when(propertyRepository.findById(propertyUUID)).thenReturn(Optional.of(property));
        when(propertyTeamMemberRepository.findById(teamMemberUUID)).thenReturn(Optional.of(propertyTeamMember));
        when(blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(any(UUID.class), anyBoolean(), any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>());
        when(bookingRepository.findActiveOrRebookedBookingsWithinDate(any(UUID.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>());
        when(blockRepository.save(any(Block.class))).thenReturn(block);

        UUID result = blockService.createBlock(teamMemberUUID, blockRequestDTO);

        assertEquals(blockUUID, result);
        verify(bookingRepository, times(1)).updateBookingsStatus(any(ArrayList.class), eq(BookingStatus.BLOCKED.getValue()));
        verify(blockRepository, times(1)).save(any(Block.class));
    }

    @Test
    public void testCreateBlockWhenOverlappingBlocksExistThenThrowExistingBlockException() {
        when(propertyRepository.findById(propertyUUID)).thenReturn(Optional.of(property));
        when(propertyTeamMemberRepository.findById(teamMemberUUID)).thenReturn(Optional.of(propertyTeamMember));
        when(blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(any(UUID.class), anyBoolean(), any(LocalDate.class), any(LocalDate.class))).thenReturn(List.of(block));

        assertThrows(ExistingBlockException.class, () -> blockService.createBlock(teamMemberUUID, blockRequestDTO));
    }

    @Test
    public void testDeleteBlock() {
        doNothing().when(blockRepository).deleteById(blockUUID);

        blockService.deleteBlock(blockUUID);

        verify(blockRepository, times(1)).deleteById(blockUUID);
    }
}