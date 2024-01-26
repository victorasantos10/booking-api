package com.hostfully.bookingapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookingapi.models.dto.request.create.PropertyRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.PropertyUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.services.PropertyService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropertyService propertyService;

    private PropertyRequestDTO propertyRequestDTO;
    private PropertyUpdateRequestDTO propertyUpdateRequestDTO;
    private UUID propertyUUID;

    @BeforeEach
    public void setUp() {
        propertyUUID = UUID.randomUUID();
        propertyRequestDTO = new PropertyRequestDTO();
        propertyRequestDTO.setName("Test Property");
        propertyRequestDTO.setAddress("Test Address");

        propertyUpdateRequestDTO = new PropertyUpdateRequestDTO();
        propertyUpdateRequestDTO.setId(propertyUUID);
        propertyUpdateRequestDTO.setName("Updated Test Property");
        propertyUpdateRequestDTO.setAddress("Updated Test Address");
    }

    @Test
    public void testCreateProperty() throws Exception {
        Mockito.when(propertyService.createProperty(propertyRequestDTO)).thenReturn(propertyUUID);

        mockMvc.perform(post("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(propertyRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").value(propertyUUID.toString()));
    }

    @Test
    public void testUpdateProperty() throws Exception {
        Mockito.doNothing().when(propertyService).updateProperty(propertyUpdateRequestDTO);

        mockMvc.perform(put("/api/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(propertyUpdateRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteProperty() throws Exception {
        Mockito.doNothing().when(propertyService).deleteProperty(propertyUUID);

        mockMvc.perform(delete("/api/properties/{propertyUUID}", propertyUUID))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllProperties() throws Exception {
        ArrayList<PropertyResponseDTO> propertyResponseDTOList = new ArrayList<>();
        PropertyResponseDTO propertyResponseDTO = new PropertyResponseDTO();
        propertyResponseDTO.setId(propertyUUID);
        propertyResponseDTO.setName("Test Property");
        propertyResponseDTO.setAddress("Test Address");
        propertyResponseDTOList.add(propertyResponseDTO);

        Mockito.when(propertyService.getAllProperties()).thenReturn(propertyResponseDTOList);

        mockMvc.perform(get("/api/properties"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(propertyUUID.toString()));
    }

    @Test
    public void testGetPropertyWhenValidUUIDThenReturnProperty() throws Exception {
        UUID propertyUUID = UUID.randomUUID();
        PropertyResponseDTO propertyResponseDTO = new PropertyResponseDTO();
        propertyResponseDTO.setId(propertyUUID);

        Mockito.when(propertyService.getPropertyById(propertyUUID)).thenReturn(propertyResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties/detail/{propertyUUID}", propertyUUID))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(propertyUUID.toString()));
    }

    @Test
    public void testGetPropertyWhenInvalidUUIDThenReturnNotFound() throws Exception {

        Mockito.when(propertyService.getPropertyById(propertyUUID)).thenThrow(new EntityNotFoundException("not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties/detail/{propertyUUID}", propertyUUID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testGetPropertyWhenNonExistentUUIDThenReturnNotFound() throws Exception {
        UUID nonExistentUUID = UUID.randomUUID();

        Mockito.when(propertyService.getPropertyById(nonExistentUUID)).thenThrow(new EntityNotFoundException("Property not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/properties/detail/{propertyUUID}", nonExistentUUID))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}