package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BookingRepository extends CrudRepository<Booking, UUID>{
}
