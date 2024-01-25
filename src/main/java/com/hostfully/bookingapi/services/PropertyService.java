package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.property.PropertyDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import com.hostfully.bookingapi.repositories.PropertyTeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyService {
    @Autowired
    PropertyRepository propertyRepository;
    @Autowired
    PropertyTeamMemberRepository propertyTeamMemberRepository;

    public Property getPropertyById(UUID propertyId){
        return propertyRepository.findById(propertyId).orElseThrow(() -> new EntityNotFoundException("Property not found"));
    }

    public ArrayList<PropertyDTO> getAllProperties(){
        return new ArrayList<>(propertyRepository.findAll().stream().map(Property::toDTO).toList());
    }

    public void updateProperty(PropertyDTO dto){
        propertyRepository.save(dto.toEntity());
    }

    public void deleteProperty(UUID propertyId){
        propertyRepository.deleteById(propertyId);
    }

    public UUID createProperty(PropertyDTO dto){
        Property prop = dto.toEntity();
        Property savedEntity = propertyRepository.save(prop);
        return savedEntity.id;
    }
}
