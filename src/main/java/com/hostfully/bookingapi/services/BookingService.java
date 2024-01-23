package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.booking.CreateBookingDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    public Optional<Booking> getBooking(UUID bookingId){
        return bookingRepository.findById(bookingId);
    }

    public void deleteBooking(UUID bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    public UUID createBooking(CreateBookingDTO dto){

        Booking savedEntity = bookingRepository.save(dto.toEntity());

        return savedEntity.id;
    }
}
