package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.guest.GuestDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.repositories.interfaces.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public Optional<Guest> getGuestById(UUID guestId){
        return guestRepository.findById(guestId);
    }

    public void updateProperty(GuestDTO dto){
        guestRepository.save(dto.toEntity());
    }

    public void deleteProperty(UUID propertyId){
        guestRepository.deleteById(propertyId);
    }

    public UUID createProperty(GuestDTO dto){
        Guest savedEntity = guestRepository.save(dto.toEntity());
        return savedEntity.id;
    }
}
