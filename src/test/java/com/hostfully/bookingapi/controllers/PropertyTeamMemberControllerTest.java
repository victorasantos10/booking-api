package com.hostfully.bookingapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.request.create.PropertyTeamMemberRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.PropertyTeamMemberUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyTeamMemberResponseDTO;
import com.hostfully.bookingapi.services.PropertyTeamMemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PropertyTeamMemberController.class)
public class PropertyTeamMemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyTeamMemberService propertyTeamMemberService;

    @Test
    public void testGetTeamMemberWhenValidUUIDThenReturnResponse() throws Exception {
        UUID uuid = UUID.randomUUID();
        PropertyTeamMemberResponseDTO responseDTO = new PropertyTeamMemberResponseDTO();
        Mockito.when(propertyTeamMemberService.getTeamMember(uuid)).thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/property-team-members/detail/{teamMemberUUID}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).getTeamMember(uuid);
    }

    @Test
    public void testGetAllTeamMembers() throws Exception {
        ArrayList<PropertyTeamMemberResponseDTO> responseDTOs = new ArrayList<>();
        Mockito.when(propertyTeamMemberService.getAllTeamMembers()).thenReturn(responseDTOs);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/property-team-members")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists());
        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).getAllTeamMembers();
    }

    @Test
    public void testCreatePropertyTeamMembersWithInvalidRequest() throws Exception {
        PropertyTeamMemberRequestDTO requestDTO = new PropertyTeamMemberRequestDTO();
        UUID uuid = UUID.randomUUID();
        Mockito.when(propertyTeamMemberService.createPropertyTeamMember(requestDTO)).thenReturn(uuid);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/property-team-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(propertyTeamMemberService, Mockito.times(0)).createPropertyTeamMember(requestDTO);
    }

    @Test
    public void testCreatePropertyTeamMembers() throws Exception {
        PropertyTeamMemberRequestDTO requestDTO = new PropertyTeamMemberRequestDTO();
        UUID uuid = UUID.randomUUID();
        requestDTO.setName("Test");
        requestDTO.setPropertyId(UUID.randomUUID());
        requestDTO.setType(TeamMemberType.MANAGER);
        Mockito.when(propertyTeamMemberService.createPropertyTeamMember(requestDTO)).thenReturn(uuid);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/property-team-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).createPropertyTeamMember(requestDTO);
    }


    @Test
    public void testUpdatePropertyTeamMembers() throws Exception {
        PropertyTeamMemberUpdateRequestDTO requestDTO = new PropertyTeamMemberUpdateRequestDTO();
        requestDTO.setId(UUID.randomUUID());
        requestDTO.setName("Test");
        requestDTO.setType(TeamMemberType.MANAGER);
        Mockito.doNothing().when(propertyTeamMemberService).updatePropertyTeamMember(requestDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/property-team-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).updatePropertyTeamMember(requestDTO);
    }


    @Test
    public void testUpdatePropertyTeamMembersWithInvalidRequest() throws Exception {
        PropertyTeamMemberUpdateRequestDTO requestDTO = new PropertyTeamMemberUpdateRequestDTO();
        Mockito.doNothing().when(propertyTeamMemberService).updatePropertyTeamMember(requestDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/property-team-members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        Mockito.verify(propertyTeamMemberService, Mockito.times(0)).updatePropertyTeamMember(requestDTO);
    }

    @Test
    public void testDeletePropertyTeamMembers() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.doNothing().when(propertyTeamMemberService).deletePropertyTeamMember(uuid);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/property-team-members/{teamMemberUUID}", uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(propertyTeamMemberService, Mockito.times(1)).deletePropertyTeamMember(uuid);
    }
}