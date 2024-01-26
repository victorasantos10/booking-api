package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.exceptions.ExistingBlockException;
import com.hostfully.bookingapi.exceptions.ExistingBookingException;
import com.hostfully.bookingapi.models.dto.BookingDTO;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    private BookingDTO bookingDTO;
    private Booking booking;
    private Property property;
    private Guest guest;

    @BeforeEach
    public void setUp() {
        bookingDTO = new BookingDTO();
        bookingDTO.setId(UUID.randomUUID());
        bookingDTO.setGuestId(UUID.randomUUID());
        bookingDTO.setPropertyId(UUID.randomUUID());
        bookingDTO.setStatus(BookingStatus.ACTIVE);
        bookingDTO.setStartDate(LocalDate.now());
        bookingDTO.setEndDate(LocalDate.now().plusDays(1));

        booking = new Booking();
        booking.setId(bookingDTO.getId());

        property = new Property();
        property.setId(bookingDTO.getPropertyId());

        guest = new Guest();
        guest.setId(bookingDTO.getGuestId());
    }

    @Test
    public void testUpdateBookingWhenBookingExistsThenBookingIsUpdated() {
        when(propertyRepository.findById(bookingDTO.getPropertyId())).thenReturn(Optional.of(property));
        when(guestRepository.findById(bookingDTO.getGuestId())).thenReturn(Optional.of(guest));
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.of(booking));

        bookingService.updateBooking(bookingDTO);

        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    public void testUpdateBookingWhenBookingDoesNotExistThenEntityNotFoundExceptionIsThrown() {
        when(propertyRepository.findById(bookingDTO.getPropertyId())).thenReturn(Optional.of(property));
        when(guestRepository.findById(bookingDTO.getGuestId())).thenReturn(Optional.of(guest));
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.updateBooking(bookingDTO));
    }

    @Test
    public void testCreateBookingWhenBookingIsValidThenBookingIsCreated() {
        when(propertyRepository.findById(bookingDTO.getPropertyId())).thenReturn(Optional.of(property));
        when(guestRepository.findById(bookingDTO.getGuestId())).thenReturn(Optional.of(guest));
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        UUID createdBookingId = bookingService.createBooking(bookingDTO);

        verify(bookingRepository, times(1)).save(any(Booking.class));
        assertEquals(booking.getId(), createdBookingId);
    }

    @Test
    public void testCreateBookingWhenBookingOverlapsExistingBookingThenExistingBookingExceptionIsThrown() {
        when(propertyRepository.findById(bookingDTO.getPropertyId())).thenReturn(Optional.of(property));
        when(guestRepository.findById(bookingDTO.getGuestId())).thenReturn(Optional.of(guest));
        when(bookingRepository.findActiveOrRebookedBookingsWithinDate(any(UUID.class), any(LocalDate.class), any(LocalDate.class))).thenReturn(new ArrayList<>(Arrays.asList(booking)));

        assertThrows(ExistingBookingException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @Test
    public void testCreateBookingWhenBookingOverlapsExistingBlockThenExistingBlockExceptionIsThrown() {
        when(propertyRepository.findById(bookingDTO.getPropertyId())).thenReturn(Optional.of(property));
        when(guestRepository.findById(bookingDTO.getGuestId())).thenReturn(Optional.of(guest));
        when(blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(any(UUID.class), anyBoolean(), any(LocalDate.class), any(LocalDate.class))).thenReturn(new Block());

        assertThrows(ExistingBlockException.class, () -> bookingService.createBooking(bookingDTO));
    }

    @Test
    public void testGetBookingWhenBookingExistsThenBookingIsReturned() {
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.of(booking));

        BookingDTO returnedBookingDTO = bookingService.getBooking(bookingDTO.getId());

        assertEquals(bookingDTO.getId(), returnedBookingDTO.getId());
    }

    @Test
    public void testGetBookingWhenBookingDoesNotExistThenEntityNotFoundExceptionIsThrown() {
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.getBooking(bookingDTO.getId()));
    }

    @Test
    public void testDeleteBookingWhenBookingExistsThenBookingIsDeleted() {
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.of(booking));

        bookingService.deleteBooking(bookingDTO.getId());

        verify(bookingRepository, times(1)).deleteById(bookingDTO.getId());
    }

    @Test
    public void testDeleteBookingWhenBookingDoesNotExistThenEntityNotFoundExceptionIsThrown() {
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.deleteBooking(bookingDTO.getId()));
    }

    @Test
    public void testCancelBookingWhenBookingExistsThenBookingIsCancelled() {
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(bookingDTO.getId());

        verify(bookingRepository, times(1)).updateBookingsStatus(any(ArrayList.class), any(Integer.class));
    }

    @Test
    public void testCancelBookingWhenBookingDoesNotExistThenEntityNotFoundExceptionIsThrown() {
        when(bookingRepository.findById(bookingDTO.getId())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.cancelBooking(bookingDTO.getId()));
    }
}