package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.repositories.PropertyTeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PropertyTeamMemberService {

    @Autowired
    PropertyTeamMemberRepository propertyTeamMemberRepository;

    public void validateTeamMember(UUID teamMemberUUID){
        propertyTeamMemberRepository.findById(teamMemberUUID).orElseThrow(() -> new EntityNotFoundException("Team member not found"));
    }
}
