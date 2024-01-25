package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.BlockDTO;
import com.hostfully.bookingapi.services.BlockService;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BlockControllerTest {

    @Mock
    private BlockService blockService;

    @Mock
    private PropertyTeamMemberService propertyTeamMemberService;

    @InjectMocks
    private BlockController blockController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetBlockByIdWhenValidUUIDsThenReturnBlockDTO() {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();
        BlockDTO blockDTO = new BlockDTO();

        doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        when(blockService.getBlock(blockUUID)).thenReturn(blockDTO);

        ResponseEntity<ApiResponseDTO<BlockDTO>> response = blockController.getBlockById(teamMemberUUID, blockUUID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blockDTO, response.getBody().getData());

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        verify(blockService).getBlock(blockUUID);
    }

    @Test
    void testGetBlockByIdWhenInvalidTeamMemberUUIDThenReturnNotFound() {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Team member not found"))
                .when(propertyTeamMemberService)
                .validateTeamMember(any(UUID.class));

        assertThrows(EntityNotFoundException.class, () -> blockController.getBlockById(teamMemberUUID, blockUUID));

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
    }

    @Test
    void testGetBlockByIdWhenInvalidBlockUUIDThenReturnNotFound() {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();

        doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        when(blockService.getBlock(any(UUID.class))).thenThrow(new EntityNotFoundException("Block not found"));

        assertThrows(EntityNotFoundException.class, () -> blockController.getBlockById(teamMemberUUID, blockUUID));

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        verify(blockService).getBlock(blockUUID);
    }

    @Test
    void testCreateBlock_shouldReturnBlockUUID() {
        UUID teamMemberUUID = UUID.randomUUID();
        BlockDTO blockDTO = new BlockDTO();
        UUID blockUUID = UUID.randomUUID();

        doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        when(blockService.createBlock(any(UUID.class), any(BlockDTO.class))).thenReturn(blockUUID);

        ResponseEntity<ApiResponseDTO<UUID>> response = blockController.createBlock(teamMemberUUID, blockDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blockUUID, response.getBody().getData());

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        verify(blockService).createBlock(teamMemberUUID, blockDTO);
    }

    @Test
    void testUpdateBlock_shouldReturnHttpStatusOk() {
        UUID teamMemberUUID = UUID.randomUUID();
        BlockDTO blockDTO = new BlockDTO();

        doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);

        ResponseEntity response = blockController.updateBlock(teamMemberUUID, blockDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        verify(blockService).updateBlock(blockDTO);
    }

    @Test
    void testDeleteBlock_shouldReturnHttpStatusOk() {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();


        doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        ResponseEntity response = blockController.deleteBlock(teamMemberUUID, blockUUID);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        verify(blockService).deleteBlock(blockUUID);
    }

    @Test
    void testGetBlockById_shouldReturnBlockDTO() {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();
        BlockDTO blockDTO = new BlockDTO();

        doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        when(blockService.getBlock(any(UUID.class))).thenReturn(blockDTO);

        ResponseEntity<ApiResponseDTO<BlockDTO>> response = blockController.getBlockById(teamMemberUUID, blockUUID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(blockDTO, response.getBody().getData());

        verify(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        verify(blockService).getBlock(blockUUID);
    }
}
