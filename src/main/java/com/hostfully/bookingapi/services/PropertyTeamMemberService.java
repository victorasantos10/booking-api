package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.PropertyTeamMemberDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import com.hostfully.bookingapi.repositories.PropertyTeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PropertyTeamMemberService {

    @Autowired
    PropertyTeamMemberRepository propertyTeamMemberRepository;

    @Autowired
    PropertyRepository propertyRepository;

    public void validateTeamMember(UUID teamMemberUUID){
        propertyTeamMemberRepository.findById(teamMemberUUID).orElseThrow(() -> new EntityNotFoundException("Team member not found"));
    }

    public PropertyTeamMemberDTO getTeamMember(UUID teamMemberUUID){
        return propertyTeamMemberRepository.findById(teamMemberUUID).orElseThrow(() -> new EntityNotFoundException("Team member not found")).toDTO();
    }

    public ArrayList<PropertyTeamMemberDTO> getAllTeamMembers(){
        return propertyTeamMemberRepository.findAll().stream().map(PropertyTeamMember::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    public void updatePropertyTeamMember(PropertyTeamMemberDTO dto){
        propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        PropertyTeamMember teamMember = propertyTeamMemberRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Team member not found"));
        propertyTeamMemberRepository.save(dto.toEntityUpdate(teamMember));
    }

    public void deletePropertyTeamMember(UUID teamMemberUUID){
        propertyTeamMemberRepository.findById(teamMemberUUID).orElseThrow(() -> new EntityNotFoundException("Team member not found"));
        propertyTeamMemberRepository.deleteById(teamMemberUUID);
    }

    public UUID createPropertyTeamMember(PropertyTeamMemberDTO dto){
        Property property = propertyRepository.findById(dto.getPropertyId()).orElseThrow(() -> new EntityNotFoundException("Property not found"));
        PropertyTeamMember savedEntity = propertyTeamMemberRepository.save(dto.toEntity(property));
        return savedEntity.getId();
    }
}
