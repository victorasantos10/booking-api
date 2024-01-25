package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.exceptions.ExistingBlockException;
import com.hostfully.bookingapi.exceptions.ExistingBookingException;
import com.hostfully.bookingapi.exceptions.OverlappingDatesException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public BookingDTO getBooking(UUID bookingId){
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        return booking.toDTO();
    }

    public void deleteBooking(UUID bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public void cancelBooking(UUID bookingId) {
        bookingRepository.updateBookingsStatus(new ArrayList<UUID>(Arrays.asList(bookingId)), BookingStatus.CANCELLED.getValue());
    }

    public UUID createBooking(BookingDTO dto){
        //Checking if all derived entities exist.
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        Guest guest = guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        validateBooking(dto);

        Booking savedEntity = bookingRepository.save(dto.toEntity(property, guest));
        return savedEntity.getId();
    }

    public void updateBooking(BookingDTO dto){
        //Checking if all derived entities exist.
        propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
        Booking booking = bookingRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        ArrayList<Booking> datesOverlapping = bookingRepository.findActiveOrRebookedBookingsWithinDate(dto.getPropertyId(), dto.getStartDateTime(), dto.getEndDateTime());
        Block blockOverlapping = blockRepository.findByPropertyIdAndIsActiveAndStartDateTimeAndEndDateTime(dto.getPropertyId(), true, dto.getStartDateTime(), dto.getEndDateTime());

        if(!datesOverlapping.isEmpty()){
            //providing a more friendly message if user already has a booking
            if(datesOverlapping.stream().anyMatch(item -> item.getGuestId() == dto.getGuestId())){
                throw new ExistingBookingException("You already have an existing booking for the same property in the same date");
            }
            throw new OverlappingDatesException("The property isn't available at those dates. Please select another date range.");
        }

        if(blockOverlapping != null){
            throw new ExistingBlockException("The property is currently blocked. Reason: " + blockOverlapping.getReason());
        }
        bookingRepository.save(dto.toEntityUpdate(booking));
    }

    private void validateBooking(BookingDTO dto) {
        ArrayList<Booking> datesOverlapping = bookingRepository.findActiveOrRebookedBookingsWithinDate(dto.getPropertyId(), dto.getStartDateTime(), dto.getEndDateTime());
        Block blockOverlapping = blockRepository.findByPropertyIdAndIsActiveAndStartDateTimeAndEndDateTime(dto.getPropertyId(), true, dto.getStartDateTime(), dto.getEndDateTime());

        if(!datesOverlapping.isEmpty()){
            //providing a more friendly message if user already has a booking
            if(datesOverlapping.stream().anyMatch(item -> item.getGuestId() == dto.getGuestId())){
                throw new ExistingBookingException("You already have an existing booking for the same property in the same date");
            }
            throw new OverlappingDatesException("The property isn't available at those dates. Please select another date range.");
        }

        if(blockOverlapping != null){
            throw new ExistingBlockException("The property is currently blocked. Reason: " + blockOverlapping.getReason());
        }
    }
}
