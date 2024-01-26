package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.request.create.BookingRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.BookingUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.BookingResponseDTO;
import com.hostfully.bookingapi.services.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.hostfully.bookingapi.helpers.ValidationHelpers.validateDateRange;

@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found"),
        @ApiResponse(responseCode = "400", description = "Bad Request")
})
@Tag(name = "Bookings API", description = "Creates a booking for a given guest and property. The bookings should not overlap on dates with existing bookings or blocks, and a guest cannot book more than one time for the same property in the same date range")
@RestController
@RequestMapping(value = "/api/bookings")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @Operation(summary = "Get a booking by ID")
    @RequestMapping(method = RequestMethod.GET, value = "/{bookingUUID}")
    public ResponseEntity<ApiResponseDTO<BookingResponseDTO>> getBookingById(@PathVariable UUID bookingUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(bookingService.getBooking(bookingUUID)));
    }

    @Operation(summary = "Create a booking")
    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> addBooking(@RequestBody @Valid BookingRequestDTO bookingRequestDto){
        validateDateRange(bookingRequestDto.getStartDate(), bookingRequestDto.getEndDate());
        return ResponseEntity.ok(new ApiResponseDTO<>(bookingService.createBooking(bookingRequestDto)));
    }

    @Operation(summary = "Update a booking")
    @PutMapping()
    public ResponseEntity updateBooking(@RequestBody @Valid BookingUpdateRequestDTO bookingRequestDto) {
        validateDateRange(bookingRequestDto.getStartDate(), bookingRequestDto.getEndDate());
        bookingService.updateBooking(bookingRequestDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete a booking")
    @DeleteMapping("/{bookingUUID}")
    public ResponseEntity deleteBooking(@PathVariable UUID bookingUUID){
        bookingService.deleteBooking(bookingUUID);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Cancel the booking", description = "Sets the booking status to CANCELLED. The user is able to reschedule this booking later if needed.")
    @PatchMapping("/{bookingUUID}")
    public ResponseEntity cancelBooking(@PathVariable UUID bookingUUID){
        bookingService.cancelBooking(bookingUUID);
        return ResponseEntity.ok().build();
    }
}
