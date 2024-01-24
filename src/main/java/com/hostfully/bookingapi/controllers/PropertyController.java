package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.property.PropertyDTO;
import com.hostfully.bookingapi.services.PropertyService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("{propertyUUID}")
    public ResponseEntity<ApiResponseDTO<PropertyDTO>> getProperty(@PathVariable UUID propertyUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.getPropertyById(propertyUUID).toDTO()));
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> createProperty(@RequestBody PropertyDTO propertyDTO){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.createProperty(propertyDTO)));
    }

    @PutMapping()
    public ResponseEntity updateProperty(@RequestBody PropertyDTO propertyDTO){
        propertyService.updateProperty(propertyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{propertyUUID}")
    public ResponseEntity deleteProperty(@PathVariable UUID propertyUUID){
        propertyService.deleteProperty(propertyUUID);
        return ResponseEntity.ok().build();
    }
}
