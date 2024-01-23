package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/guests")
public class GuestController {

    @Autowired
    GuestService guestService;

//    public ResponseEntity<ApiResponseDTO<Guest>>
}
