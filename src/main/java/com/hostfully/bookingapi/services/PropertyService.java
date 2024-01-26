package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.request.create.PropertyRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.PropertyUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;

    public PropertyResponseDTO getPropertyById(UUID propertyId){
        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        return property.toDTO();
    }

    public ArrayList<PropertyResponseDTO> getAllProperties(){
        return new ArrayList<>(propertyRepository.findAll().stream().map(Property::toDTO).toList());
    }

    public void updateProperty(PropertyUpdateRequestDTO dto){
        Property property = propertyRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        propertyRepository.save(dto.toEntityUpdate(property));
    }

    public void deleteProperty(UUID propertyId){
        propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        propertyRepository.deleteById(propertyId);
    }

    public UUID createProperty(PropertyRequestDTO dto){
        Property savedEntity = propertyRepository.save(dto.toEntity());
        return savedEntity.id;
    }
}
