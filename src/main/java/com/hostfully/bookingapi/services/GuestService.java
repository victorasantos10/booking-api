package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.guest.GuestDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public GuestDTO getGuestById(UUID guestId){
        return guestRepository.findById(guestId).map(Guest::toDTO).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
    }

    public ArrayList<GuestDTO> getAllGuests(){
        return new ArrayList<>(guestRepository.findAll().stream().map(Guest::toDTO).toList());
    }

    public void updateGuest(GuestDTO dto){
        guestRepository.save(dto.toEntity());
    }

    public void deleteGuest(UUID propertyId){
        guestRepository.deleteById(propertyId);
    }

    public UUID createGuest(GuestDTO dto){
        Guest savedEntity = guestRepository.save(dto.toEntity());
        return savedEntity.id;
    }
}
