package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.enums.BookingStatus;
import com.hostfully.bookingapi.exceptions.OverlappingDatesException;
import com.hostfully.bookingapi.models.dto.booking.BookingDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.repositories.BookingRepository;
import com.hostfully.bookingapi.repositories.GuestRepository;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
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
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        booking.status = BookingStatus.CANCELLED;

        bookingRepository.save(booking);
    }

    public UUID createOrUpdateBooking(BookingDTO dto){
        //Checking if all derived entities exist.
        propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        // Checking if reservation dates are overlapping with any existing active bookings
        boolean datesOverlapping = bookingRepository.areDatesOverlapping(dto.getPropertyId(), dto.getStartDateTime(), dto.getEndDateTime());

        if(datesOverlapping){
            throw new OverlappingDatesException("The property isn't available at those dates. Please select another date range.");
        }


        Booking savedEntity = bookingRepository.save(dto.toEntity());
        return savedEntity.id;
    }
}
