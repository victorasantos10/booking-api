package com.hostfully.bookingapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookingapi.models.dto.request.create.GuestRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.GuestUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.GuestResponseDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyResponseDTO;
import com.hostfully.bookingapi.services.GuestService;
import jakarta.persistence.EntityNotFoundException;
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
import java.util.ArrayList;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GuestService guestService;

    @Test
    public void testGetGuestByIdWhenValidGuestUUIDThenReturnGuestResponseDTO() throws Exception {
        UUID guestUUID = UUID.randomUUID();
        GuestResponseDTO guestResponseDTO = new GuestResponseDTO();
        guestResponseDTO.setId(guestUUID);

        when(guestService.getGuestById(guestUUID)).thenReturn(guestResponseDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/{guestUUID}", guestUUID))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(guestUUID.toString()));
    }

    @Test
    public void testGetGuestByIdWhenInvalidGuestUUIDThenReturnNotFound() throws Exception {
        UUID invalidGuestUUID = UUID.randomUUID();

        when(guestService.getGuestById(invalidGuestUUID)).thenThrow(new EntityNotFoundException("Not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/{guestUUID}", invalidGuestUUID))
                .andExpect(status().isNotFound());

        verify(guestService, times(1)).getGuestById(invalidGuestUUID);
    }

    @Test
    public void testGetAllGuests() throws Exception {
        ArrayList<GuestResponseDTO> guestResponseDTOList = new ArrayList<>();
        UUID guestUUID = UUID.randomUUID();
        GuestResponseDTO guestResponseDTO = new GuestResponseDTO();
        guestResponseDTO.setId(guestUUID);
        guestResponseDTO.setName("Guest test");
        guestResponseDTOList.add(guestResponseDTO);

        Mockito.when(guestService.getAllGuests()).thenReturn(guestResponseDTOList);

        mockMvc.perform(get("/api/guests"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(guestResponseDTO.getId().toString()));
    }

    @Test
    public void testAddGuestWhenInvalidRequestThenReturnBadRequest() throws Exception {
        GuestRequestDTO guestRequestDTO = new GuestRequestDTO();
        guestRequestDTO.setName(null);
        guestRequestDTO.setDateOfBirth(LocalDate.of(1895, 3, 5));
        guestRequestDTO.setEmail("test@test.com");
        guestRequestDTO.setPhone("+1234546");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testAddGuestWhenValidRequestThenReturnOk() throws Exception {
        GuestRequestDTO guestRequestDTO = new GuestRequestDTO();
        guestRequestDTO.setName("test guest");
        guestRequestDTO.setDateOfBirth(LocalDate.of(1995, 3, 5));
        guestRequestDTO.setEmail("test@test.com");
        guestRequestDTO.setPhone("+1234546");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateGuestWhenValidRequestThenReturnOk() throws Exception {
        GuestUpdateRequestDTO guestUpdateRequestDTO = new GuestUpdateRequestDTO();
        guestUpdateRequestDTO.setId(UUID.randomUUID());
        guestUpdateRequestDTO.setName("Updated Test");
        guestUpdateRequestDTO.setDateOfBirth(LocalDate.of(1995, 3, 5));
        guestUpdateRequestDTO.setEmail("updated@test.com");
        guestUpdateRequestDTO.setPhone("+1234546");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/guests")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(guestUpdateRequestDTO)))
                .andExpect(status().isOk());

        verify(guestService, times(1)).updateGuest(guestUpdateRequestDTO);
    }

    @Test
    public void testDeleteGuestWhenValidGuestUUIDThenReturnOk() throws Exception {
        UUID guestUUID = UUID.randomUUID();

        doNothing().when(guestService).deleteGuest(guestUUID);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/guests/{guestUUID}", guestUUID))
                .andExpect(status().isOk());

        verify(guestService, times(1)).deleteGuest(guestUUID);
    }
}