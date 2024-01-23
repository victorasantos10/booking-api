package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.helpers.ValidationHelpers;
import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.booking.CreateBookingDTO;
import com.hostfully.bookingapi.models.dto.booking.UpdateBookingDTO;
import com.hostfully.bookingapi.services.BookingService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@RestController
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
    @RequestMapping(method = RequestMethod.GET, value = "/api/bookings/{bookingUUID}")
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

    @PostMapping("/api/bookings")
    public ResponseEntity<ApiResponseDTO<UUID>> addBooking(@RequestBody CreateBookingDTO createBookingDto){
        try {
            return ResponseEntity.ok(new ApiResponseDTO<>(bookingService.createBooking(createBookingDto)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDTO<>(null, new ArrayList<Object>(Arrays.asList(e.getMessage()))));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponseDTO<>(null, new ArrayList<>(Arrays.asList("An error occurred"))));
        }
        /*TODO:
            - Managers and owners should not be able to call this
            - Validate if bookingUUID is valid, and return
                - 200 (OK) if there is a booking with that ID
                - 404 if the bookingUUID is valid but no resource was vound
                - (nice to have): Log activity
          */
    }

//    @RequestMapping(method = RequestMethod.PUT)
//    public void updateBooking(@RequestBody UpdateBookingDTO updateBookingDto) {
//        /*TODO:
//            - Managers and owners should not be able to call this
//            - Validate if bookingUUID is valid, and return
//                - 200 (OK) if there is a booking with that ID
//                - 404 if the bookingUUID is valid but no resource was vound
//                - (nice to have): Log activity
//          */
//    }
//
    @RequestMapping(method = RequestMethod.DELETE, value = "/{bookingUUID}")
    public void deleteBooking(@PathVariable UUID bookingUUID){
        bookingService.deleteBooking(bookingUUID);
    }



}
