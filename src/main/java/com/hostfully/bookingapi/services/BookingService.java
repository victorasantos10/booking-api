package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.exceptions.OverlappingDatesException;
import com.hostfully.bookingapi.models.dto.booking.CreateBookingDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.repositories.interfaces.BookingRepository;
import com.hostfully.bookingapi.repositories.interfaces.GuestRepository;
import com.hostfully.bookingapi.repositories.interfaces.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.hibernate.sql.ast.tree.expression.Over;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    GuestRepository guestRepository;

    public Optional<Booking> getBooking(UUID bookingId){
        return bookingRepository.findById(bookingId);
    }

    public void deleteBooking(UUID bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public UUID createBooking(CreateBookingDTO dto){
        //Checking if all derived entities exist.
        propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        guestRepository.findById(dto.getGuestId()).orElseThrow(() -> new EntityNotFoundException("Guest not found"));

        // Checking if reservation dates are overlapping with any existing active bookings
        boolean datesOverlapping = bookingRepository.areDatesOverlapping(dto.getPropertyId(), dto.getStartDateTime(), dto.getEndDateTime());

        if(datesOverlapping){
            throw new OverlappingDatesException("The property is already booked at those dates. Please select another date range.");
        }


        Booking savedEntity = bookingRepository.save(dto.toEntity());
        return savedEntity.id;
    }
}
