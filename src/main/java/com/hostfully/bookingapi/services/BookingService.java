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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    BlockRepository blockRepository;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    GuestRepository guestRepository;

    public BookingResponseDTO getBooking(UUID bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        return booking.toDTO();
    }

    public void deleteBooking(UUID bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public void cancelBooking(UUID bookingId) {
        bookingRepository.updateBookingsStatus(new ArrayList<UUID>(Arrays.asList(bookingId)), BookingStatus.CANCELLED.getValue());
    }

    public UUID createBooking(BookingRequestDTO dto){
        //Checking if all derived entities exist.
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        Guest guest = guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        ArrayList<Booking> datesOverlapping = bookingRepository.findActiveOrRebookedBookingsWithinDate(dto.getPropertyId(), dto.getStartDate(), dto.getEndDate());
        List<Block> blockOverlapping = blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(dto.getPropertyId(), true, dto.getStartDate(), dto.getEndDate());

        validateBooking(datesOverlapping, guest.getId(), blockOverlapping, false);

        Booking savedEntity = bookingRepository.save(dto.toEntity(property, guest));
        return savedEntity.getId();
    }

    public void updateBooking(BookingUpdateRequestDTO dto){
        //Checking if all derived entities exist.
        Booking booking = bookingRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        ArrayList<Booking> datesOverlapping = bookingRepository.findActiveOrRebookedBookingsWithinDate(booking.getPropertyId(), dto.getStartDate(), dto.getEndDate());
        List<Block> blockOverlapping = blockRepository.findByPropertyIdAndIsActiveAndStartDateAndEndDate(booking.getPropertyId(), true, dto.getStartDate(), dto.getEndDate());

        validateBooking(datesOverlapping, booking.getGuestId(), blockOverlapping, true);

        //In case user is trying to update a previous blocked, rebooked or cancelled booking to an active status (it should be rebooked)
        if(BookingStatus.valueOf(booking.getStatus()) != BookingStatus.ACTIVE && dto.getStatus() == BookingStatus.ACTIVE){
            throw new InvalidBookingOperation("A blocked, rebooked or cancelled booking can't be set to ACTIVE. Please set it to REBOOKED instead");
        }

        bookingRepository.save(dto.toEntityUpdate(booking));
    }

    private static void validateBooking(ArrayList<Booking> datesOverlapping, UUID guestId, List<Block> blockOverlapping, boolean isUpdate) {
        if(!datesOverlapping.isEmpty()){
            //providing a more friendly message if user already has a booking
            if(datesOverlapping.stream().anyMatch(item -> item.getGuestId().equals(guestId))){
                // If it is an update operation and the user already have a booking in the same date, let him change the dates
                if(isUpdate) return;
                // If it is a create operation, it means that the user is trying to create a duplicate booking on the same date
                throw new ExistingBookingException("You already have an existing booking for the same property in the same date");
            }
            //In case user is trying to book in a date that has active bookings from other guests
            throw new OverlappingDatesException("The property isn't available at those dates. Please select another date range.");
        }

        //In case user is trying to book in a date that has active blocks
        if(!blockOverlapping.isEmpty()){
            throw new ExistingBlockException("The property will be blocked in the selected dates. Reason: " + blockOverlapping.getFirst().getReason());
        }
    }
}
