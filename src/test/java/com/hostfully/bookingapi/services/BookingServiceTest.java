package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.exceptions.ExistingBlockException;
import com.hostfully.bookingapi.exceptions.ExistingBookingException;
import com.hostfully.bookingapi.exceptions.InvalidBookingOperation;
import com.hostfully.bookingapi.exceptions.OverlappingDatesException;
import com.hostfully.bookingapi.models.dto.request.create.BookingRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.BookingUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.BookingResponseDTO;
import com.hostfully.bookingapi.models.entity.Block;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.repositories.BlockRepository;
import com.hostfully.bookingapi.repositories.BookingRepository;
import com.hostfully.bookingapi.repositories.GuestRepository;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.print.Book;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private BlockRepository blockRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @Mock
    private GuestRepository guestRepository;

    @InjectMocks
    private BookingService bookingService;

    private UUID bookingId;
    private Booking booking;

    private Property property;

    @BeforeEach
    public void setUp() {
        property = new Property();
        property.setId(UUID.randomUUID());

        bookingId = UUID.randomUUID();
        booking = new Booking();

        Guest guest = new Guest();
        guest.setId(UUID.randomUUID());

        booking.setId(bookingId);
        booking.setStatus(1);
        booking.setGuest(guest);
        booking.setProperty(property);
    }

    @Test
    public void testGetBookingWhenBookingExistsThenReturnBookingResponseDTO() {
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        BookingResponseDTO bookingResponseDTO = bookingService.getBooking(bookingId);

        assertEquals(bookingId, bookingResponseDTO.getId());
    }

    @Test
    public void testGetBookingWhenBookingDoesNotExistThenThrowEntityNotFoundException() {
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.getBooking(bookingId));
    }

    @Test
    public void testDeleteBooking() {
        bookingService.deleteBooking(bookingId);
        verify(bookingRepository, times(1)).deleteById(bookingId);
    }

    @Test
    public void testCancelBooking() {
        bookingService.cancelBooking(bookingId);
        verify(bookingRepository, times(1)).updateBookingsStatus(new ArrayList<UUID>(Arrays.asList(bookingId)), BookingStatus.CANCELLED.getValue());
    }

    @Test
    public void testCreateBooking() {
        BookingRequestDTO dto = new BookingRequestDTO();
        dto.setGuestId(UUID.randomUUID());
        dto.setPropertyId(UUID.randomUUID());
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(1));
        dto.setStatus(BookingStatus.ACTIVE);
        dto.setAdults(1);
        dto.setChildren(0);

        Booking savedEntity = new Booking();
        savedEntity.setId(UUID.randomUUID());

        when(bookingRepository.save(any(Booking.class))).thenReturn(savedEntity);
        when(propertyRepository.findById(dto.getPropertyId())).thenReturn(Optional.of(new Property()));
        when(guestRepository.findById(dto.getGuestId())).thenReturn(Optional.of(new Guest()));
        when(bookingRepository.findActiveOrRebookedBookingsWithinDate(dto.getPropertyId(), dto.getStartDate(), dto.getEndDate())).thenReturn(new ArrayList<>());
        when(blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(dto.getPropertyId(), true, dto.getStartDate(), dto.getEndDate())).thenReturn(new ArrayList<>());

        UUID bookingId = bookingService.createBooking(dto);

        assertNotNull(bookingId);
    }

    @Test
    public void testUpdateBooking() {
        BookingUpdateRequestDTO dto = new BookingUpdateRequestDTO();
        dto.setId(UUID.randomUUID());
        dto.setStartDate(LocalDate.now());
        dto.setEndDate(LocalDate.now().plusDays(1));
        dto.setStatus(BookingStatus.ACTIVE);
        dto.setAdults(1);
        dto.setChildren(0);

        Booking foundBooking = new Booking();
        foundBooking.setStatus(1);

        when(bookingRepository.findById(dto.getId())).thenReturn(Optional.of(foundBooking));
        when(bookingRepository.findActiveOrRebookedBookingsWithinDate(booking.getPropertyId(), dto.getStartDate(), dto.getEndDate())).thenReturn(new ArrayList<>());
        when(blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(booking.getPropertyId(), true, dto.getStartDate(), dto.getEndDate())).thenReturn(new ArrayList<>());

        bookingService.updateBooking(dto);

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }
}