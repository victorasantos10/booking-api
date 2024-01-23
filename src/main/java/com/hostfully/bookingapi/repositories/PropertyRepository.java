package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Property;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyRepository extends CrudRepository<Property, UUID> {
}
