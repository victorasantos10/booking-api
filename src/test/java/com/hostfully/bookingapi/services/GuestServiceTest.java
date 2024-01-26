package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.request.create.GuestRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.GuestUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.GuestResponseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private GuestService guestService;

    private Guest guest;
    private UUID guestId;

    @BeforeEach
    public void setUp() {
        guestId = UUID.randomUUID();
        guest = new Guest();
        guest.setId(guestId);
        guest.setName("John Doe");
        guest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        guest.setEmail("john.doe@example.com");
        guest.setPhone("1234567890");
    }

    @Test
    @DisplayName("Test getGuestById when guest exists then return GuestResponseDTO")
    public void testGetGuestByIdWhenGuestExistsThenReturnGuestResponseDTO() {
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        GuestResponseDTO guestResponseDTO = guestService.getGuestById(guestId);

        assertThat(guestResponseDTO).isNotNull();
        assertThat(guestResponseDTO.getId()).isEqualTo(guestId);
        assertThat(guestResponseDTO.getName()).isEqualTo(guest.getName());
        assertThat(guestResponseDTO.getDateOfBirth()).isEqualTo(guest.getDateOfBirth());
        assertThat(guestResponseDTO.getEmail()).isEqualTo(guest.getEmail());
        assertThat(guestResponseDTO.getPhone()).isEqualTo(guest.getPhone());
    }

    @Test
    @DisplayName("Test getGuestById when guest does not exist then throw EntityNotFoundException")
    public void testGetGuestByIdWhenGuestDoesNotExistThenThrowEntityNotFoundException() {
        when(guestRepository.findById(guestId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> guestService.getGuestById(guestId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Guest not found");
    }

    @Test
    @DisplayName("Test getAllGuests when guests exist then return list of GuestResponseDTO")
    public void testGetAllGuestsWhenGuestsExistThenReturnListOfGuestResponseDTO() {
        ArrayList<Guest> guests = new ArrayList<>();
        guests.add(guest);
        when(guestRepository.findAll()).thenReturn(guests);

        ArrayList<GuestResponseDTO> guestResponseDTOs = guestService.getAllGuests();

        assertThat(guestResponseDTOs).isNotNull();
        assertThat(guestResponseDTOs.size()).isEqualTo(1);
        assertThat(guestResponseDTOs.get(0).getId()).isEqualTo(guestId);
    }

    @Test
    @DisplayName("Test updateGuest when guest exists then update successfully")
    public void testUpdateGuestWhenGuestExistsThenUpdateSuccessfully() {
        GuestUpdateRequestDTO guestUpdateRequestDTO = new GuestUpdateRequestDTO();
        guestUpdateRequestDTO.setId(guestId);
        guestUpdateRequestDTO.setName("Updated Name");

        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));
        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        guestService.updateGuest(guestUpdateRequestDTO);

        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    @DisplayName("Test deleteGuest when guest exists then delete successfully")
    public void testDeleteGuestWhenGuestExistsThenDeleteSuccessfully() {
        when(guestRepository.findById(guestId)).thenReturn(Optional.of(guest));

        guestService.deleteGuest(guestId);

        verify(guestRepository, times(1)).deleteById(guestId);
    }

    @Test
    @DisplayName("Test createGuest when guest data is valid then return guestId")
    public void testCreateGuestWhenGuestDataIsValidThenReturnGuestId() {
        GuestRequestDTO guestRequestDTO = new GuestRequestDTO();
        guestRequestDTO.setName("New Guest");
        guestRequestDTO.setDateOfBirth(LocalDate.of(1990, 1, 1));
        guestRequestDTO.setEmail("new.guest@example.com");
        guestRequestDTO.setPhone("1234567890");

        when(guestRepository.save(any(Guest.class))).thenReturn(guest);

        UUID createdGuestId = guestService.createGuest(guestRequestDTO);

        assertThat(createdGuestId).isEqualTo(guestId);
    }
}