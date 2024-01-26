package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.request.create.GuestRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.GuestUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.GuestResponseDTO;
import com.hostfully.bookingapi.services.GuestService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@Tag(name = "Guest API", description = "Creates a new guest in the system")
@RequestMapping(value = "/api/guests")
public class GuestController {

    @Autowired
    GuestService guestService;

    @GetMapping("/detail/{guestUUID}")
    public ResponseEntity<ApiResponseDTO<GuestResponseDTO>> getGuestById(@PathVariable UUID guestUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(guestService.getGuestById(guestUUID)));
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<ArrayList<GuestResponseDTO>>> getGuestById(){
        return ResponseEntity.ok(new ApiResponseDTO<>(guestService.getAllGuests()));
    }


    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> createGuest(@RequestBody @Valid GuestRequestDTO guestDTO){
        return ResponseEntity.ok(new ApiResponseDTO<>(guestService.createGuest(guestDTO)));
    }

    @PutMapping()
    public ResponseEntity updateGuest(@RequestBody @Valid GuestUpdateRequestDTO guestDTO){
        guestService.updateGuest(guestDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{guestUUID}")
    public ResponseEntity deleteGuest(@PathVariable UUID guestUUID){
        guestService.deleteGuest(guestUUID);
        return ResponseEntity.ok().build();
    }
}
