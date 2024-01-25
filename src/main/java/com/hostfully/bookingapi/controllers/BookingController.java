package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.BookingDTO;
import com.hostfully.bookingapi.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @RequestMapping(method = RequestMethod.GET, value = "/detail/{bookingUUID}")
    public Object getBookingById(@PathVariable UUID bookingUUID){
        return bookingService.getBooking(bookingUUID);
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> addBooking(@RequestBody BookingDTO bookingDto){
        return ResponseEntity.ok(new ApiResponseDTO<>(bookingService.createBooking(bookingDto)));
    }

    @PutMapping()
    public ResponseEntity updateBooking(@RequestBody BookingDTO bookingDto) {
        bookingService.updateBooking(bookingDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookingUUID}")
    public ResponseEntity deleteBooking(@PathVariable UUID bookingUUID){
        bookingService.deleteBooking(bookingUUID);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{bookingUUID}")
    public ResponseEntity cancelBooking(@PathVariable UUID bookingUUID){
        bookingService.cancelBooking(bookingUUID);
        return ResponseEntity.ok().build();
    }
}
