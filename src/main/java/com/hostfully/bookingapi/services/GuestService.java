package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.request.create.GuestRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.GuestUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.GuestResponseDTO;
import com.hostfully.bookingapi.models.entity.Guest;
import com.hostfully.bookingapi.repositories.GuestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class GuestService {

    @Autowired
    GuestRepository guestRepository;

    public GuestResponseDTO getGuestById(UUID guestId){
        return guestRepository.findById(guestId).map(Guest::toDTO).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
    }

    public ArrayList<GuestResponseDTO> getAllGuests(){
        return new ArrayList<>(guestRepository.findAll().stream().map(Guest::toDTO).toList());
    }

    public void updateGuest(GuestUpdateRequestDTO dto){
        Guest guest = guestRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
        guestRepository.save(dto.toEntityUpdate(guest));
    }

    public void deleteGuest(UUID guestId){
        guestRepository.findById(guestId).orElseThrow(() -> new EntityNotFoundException("Guest not found"));
        guestRepository.deleteById(guestId);
    }

    public UUID createGuest(GuestRequestDTO dto){
        Guest savedEntity = guestRepository.save(dto.toEntity());
        return savedEntity.getId();
    }
}
