package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.booking.BookingDTO;
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

//    @RequestMapping("/user/{userId}")
//    public ArrayList<Object> getBookingsForUser() {
//        /*TODO:
//            - Implement UUID for users, so an user can't figure out another user's ID
//            - Managers and owners could access a specific user's booking list FOR THEIR PROPERTY. So they can call this and see only their properties' bookings.
//            - Validate if userId is valid, and return
//                - 200 (OK) if there is available bookings
//                - 204 (No Content) if the user is valid, but there's no bookings.
//                - 400 if the user is not valid
//                - (nice to have): Log activity
//          */
//        return null;
//    }
//
    @RequestMapping(method = RequestMethod.GET, value = "{bookingUUID}")
    public Object getBookingById(@PathVariable UUID bookingUUID){
        return bookingService.getBooking(bookingUUID);
        /*TODO:
            - Managers and owners could access a specific user's booking ONLY FOR THEIR PROPERTIES, so they could call this as well
            - Validate if bookingUUID is valid, and return
                - 200 (OK) if there is a booking with that ID
                - 404 if the bookingUUID is valid but no resource was vound
                - (nice to have): Log activity
          */
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> addBooking(@RequestBody BookingDTO bookingDto){
        return ResponseEntity.ok(new ApiResponseDTO<>(bookingService.createOrUpdateBooking(bookingDto)));
    }

    @PutMapping()
    public ResponseEntity updateBooking(@RequestBody BookingDTO bookingDto) {
        bookingService.createOrUpdateBooking(bookingDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{bookingUUID}")
    public ResponseEntity cancelBooking(@PathVariable UUID bookingUUID){
        bookingService.cancelBooking(bookingUUID);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{bookingUUID}")
    public void deleteBooking(@PathVariable UUID bookingUUID){
        bookingService.deleteBooking(bookingUUID);
    }
}
