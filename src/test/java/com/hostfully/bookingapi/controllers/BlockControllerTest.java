package com.hostfully.bookingapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hostfully.bookingapi.models.dto.request.create.BlockRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.BlockUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.BlockResponseDTO;
import com.hostfully.bookingapi.services.BlockService;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BlockController.class)
public class BlockControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlockService blockService;

    @MockBean
    private PropertyTeamMemberService propertyTeamMemberService;

    private UUID teamMemberUUID;
    private UUID blockUUID;
    private BlockRequestDTO blockRequestDTO;
    private BlockUpdateRequestDTO blockUpdateRequestDTO;

    @BeforeEach
    public void setup() {
        teamMemberUUID = UUID.randomUUID();
        blockUUID = UUID.randomUUID();

        blockRequestDTO = new BlockRequestDTO();
        blockRequestDTO.setPropertyId(UUID.fromString("91b23fb9-d079-40aa-84e5-4c438ce99411"));
        blockRequestDTO.setReason("Maintenance");
        blockRequestDTO.setStartDate(LocalDate.now());
        blockRequestDTO.setEndDate(LocalDate.now().plusDays(1));

        blockUpdateRequestDTO = new BlockUpdateRequestDTO();
        blockUpdateRequestDTO.setId(blockUUID);
        blockUpdateRequestDTO.setReason("Maintenance");
        blockUpdateRequestDTO.setStartDate(LocalDate.now());
        blockUpdateRequestDTO.setEndDate(LocalDate.now().plusDays(1));
    }

    @Test
    public void testCreateBlock() throws Exception {
        Mockito.doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        when(blockService.createBlock(teamMemberUUID, blockRequestDTO)).thenReturn(blockUUID);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/blocks/{teamMemberUUID}", teamMemberUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(blockRequestDTO)))
                .andExpect(status().isOk());

        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).validateTeamMember(teamMemberUUID);
        Mockito.verify(blockService, Mockito.times(1)).createBlock(teamMemberUUID, blockRequestDTO);
    }

    @Test
    public void testUpdateBlock() throws Exception {
        Mockito.doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        Mockito.doNothing().when(blockService).updateBlock(blockUpdateRequestDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/blocks/{teamMemberUUID}", teamMemberUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(blockUpdateRequestDTO)))
                .andExpect(status().isOk());

        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).validateTeamMember(teamMemberUUID);
        Mockito.verify(blockService, Mockito.times(1)).updateBlock(blockUpdateRequestDTO);
    }

    @Test
    public void testDeleteBlock() throws Exception {
        Mockito.doNothing().when(propertyTeamMemberService).validateTeamMember(teamMemberUUID);
        Mockito.doNothing().when(blockService).deleteBlock(blockUUID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/blocks/{teamMemberUUID}/{blockUUID}", teamMemberUUID, blockUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).validateTeamMember(teamMemberUUID);
        Mockito.verify(blockService, Mockito.times(1)).deleteBlock(blockUUID);
    }

    @Test
    public void testGetBlockByIdWhenBlockExistsThenReturnBlockResponseDTO() throws Exception {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();
        BlockResponseDTO blockResponseDTO = new BlockResponseDTO();

        doNothing().when(propertyTeamMemberService).validateTeamMember(any(UUID.class));
        when(blockService.getBlock(any(UUID.class))).thenReturn(blockResponseDTO);

        mockMvc.perform(get("/api/blocks/{teamMemberUUID}/detail/{blockUUID}", teamMemberUUID, blockUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBlockByIdWhenTeamMemberNotFoundThenReturnNotFound() throws Exception {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();

        doThrow(new EntityNotFoundException("Team member not found")).when(propertyTeamMemberService).validateTeamMember(any(UUID.class));

        mockMvc.perform(get("/api/blocks/{teamMemberUUID}/detail/{blockUUID}", teamMemberUUID, blockUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetBlockByIdWhenBlockNotFoundThenReturnNotFound() throws Exception {
        UUID teamMemberUUID = UUID.randomUUID();
        UUID blockUUID = UUID.randomUUID();

        doNothing().when(propertyTeamMemberService).validateTeamMember(any(UUID.class));
        when(blockService.getBlock(any(UUID.class))).thenThrow(new EntityNotFoundException("Block not found"));

        mockMvc.perform(get("/api/blocks/{teamMemberUUID}/detail/{blockUUID}", teamMemberUUID, blockUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}