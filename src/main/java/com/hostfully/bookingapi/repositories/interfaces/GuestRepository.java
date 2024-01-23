package com.hostfully.bookingapi.repositories.interfaces;

import com.hostfully.bookingapi.models.entity.Guest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuestRepository extends CrudRepository<Guest, UUID> {
}
