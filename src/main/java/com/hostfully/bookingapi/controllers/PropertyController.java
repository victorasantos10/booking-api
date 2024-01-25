package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.PropertyDTO;
import com.hostfully.bookingapi.services.PropertyService;
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

    @GetMapping("/detail/{propertyUUID}")
    public ResponseEntity<ApiResponseDTO<PropertyDTO>> getProperty(@PathVariable UUID propertyUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.getPropertyById(propertyUUID).toDTO()));
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<ArrayList<PropertyDTO>>> getAllProperties(){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.getAllProperties()));
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
