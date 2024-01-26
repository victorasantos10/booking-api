package com.hostfully.bookingapi.controllers;

import com.hostfully.bookingapi.models.dto.ApiResponseDTO;
import com.hostfully.bookingapi.models.dto.request.create.PropertyRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.PropertyUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyResponseDTO;
import com.hostfully.bookingapi.services.PropertyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.UUID;

@Validated
@RestController
@Tag(name = "Property API", description = "Creates a new property in the system")
@RequestMapping(value = "/api/properties")
public class PropertyController {

    @Autowired
    PropertyService propertyService;

    @GetMapping("/detail/{propertyUUID}")
    public ResponseEntity<ApiResponseDTO<PropertyResponseDTO>> getProperty(@PathVariable UUID propertyUUID){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.getPropertyById(propertyUUID)));
    }

    @GetMapping()
    public ResponseEntity<ApiResponseDTO<ArrayList<PropertyResponseDTO>>> getAllProperties(){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.getAllProperties()));
    }

    @PostMapping()
    public ResponseEntity<ApiResponseDTO<UUID>> createProperty(@RequestBody @Valid PropertyRequestDTO propertyDTO){
        return ResponseEntity.ok(new ApiResponseDTO<>(propertyService.createProperty(propertyDTO)));
    }

    @PutMapping()
    public ResponseEntity updateProperty(@RequestBody @Valid PropertyUpdateRequestDTO propertyDTO){
        propertyService.updateProperty(propertyDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{propertyUUID}")
    public ResponseEntity deleteProperty(@PathVariable UUID propertyUUID){
        propertyService.deleteProperty(propertyUUID);
        return ResponseEntity.ok().build();
    }
}
