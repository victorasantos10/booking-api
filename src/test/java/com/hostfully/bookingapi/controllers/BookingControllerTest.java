package com.hostfully.bookingapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.request.create.BookingRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.BookingUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.BookingResponseDTO;
import com.hostfully.bookingapi.services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    private UUID validUUID;
    private UUID invalidUUID;
    private BookingResponseDTO bookingResponseDTO;
    private ApiResponseDTO<BookingResponseDTO> apiResponseDTO;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        validUUID = UUID.randomUUID();
        invalidUUID = UUID.randomUUID();
        bookingResponseDTO = new BookingResponseDTO();
        apiResponseDTO = new ApiResponseDTO<>(bookingResponseDTO);
    }

    @Test
    public void testAddBookingWhenValidRequestThenReturnUUID() throws Exception {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setGuestId(UUID.randomUUID());
        bookingRequestDTO.setStatus(BookingStatus.ACTIVE);
        bookingRequestDTO.setPropertyId(UUID.randomUUID());
        bookingRequestDTO.setStartDate(LocalDate.now().plusDays(1));
        bookingRequestDTO.setEndDate(LocalDate.now().plusDays(5));
        bookingRequestDTO.setAdults(2);
        bookingRequestDTO.setChildren(1);

        when(bookingService.createBooking(bookingRequestDTO)).thenReturn(validUUID);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequestDTO)))
                .andExpect(status().isOk());

        verify(bookingService,times(1)).createBooking(bookingRequestDTO);
    }

    @Test
    public void testAddBookingWhenInvalidRequestThenReturnBadRequest() throws Exception {
        BookingRequestDTO bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setGuestId(null);
        bookingRequestDTO.setStatus(BookingStatus.ACTIVE);
        bookingRequestDTO.setPropertyId(UUID.randomUUID());
        bookingRequestDTO.setStartDate(LocalDate.now().plusDays(1));
        bookingRequestDTO.setEndDate(LocalDate.now().plusDays(5));
        bookingRequestDTO.setAdults(2);
        bookingRequestDTO.setChildren(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequestDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBookingByIdWhenValidUUIDThenReturnBooking() throws Exception {
        when(bookingService.getBooking(validUUID)).thenReturn(bookingResponseDTO);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/{bookingUUID}", validUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(bookingService,times(1)).getBooking(validUUID);
    }

    @Test
    public void testGetBookingByIdWhenInvalidUUIDThenReturnNotFound() throws Exception {
        when(bookingService.getBooking(invalidUUID)).thenThrow(new EntityNotFoundException("Booking not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/bookings/{bookingUUID}", invalidUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(bookingService,times(1)).getBooking(invalidUUID);
    }

    @Test
    public void testUpdateBookingWhenValidRequestThenReturnOk() throws Exception {
        BookingUpdateRequestDTO bookingUpdateRequestDTO = new BookingUpdateRequestDTO();
        bookingUpdateRequestDTO.setId(validUUID);
        bookingUpdateRequestDTO.setStatus(BookingStatus.ACTIVE);
        bookingUpdateRequestDTO.setStartDate(LocalDate.now().plusDays(1));
        bookingUpdateRequestDTO.setEndDate(LocalDate.now().plusDays(5));
        bookingUpdateRequestDTO.setAdults(2);
        bookingUpdateRequestDTO.setChildren(1);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingUpdateRequestDTO)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteBookingWhenValidUUIDThenReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/bookings/{bookingUUID}", validUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelBookingWhenValidUUIDThenReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/bookings/{bookingUUID}", validUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}