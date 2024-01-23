package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.services.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;
}
