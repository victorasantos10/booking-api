package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends CrudRepository<Booking, UUID> {

    //Filtering all active bookings by propertyId that are overlapping with the incoming booking dates
    Optional<Booking> findByGuestIdAndPropertyIdAndStartDateTimeAndEndDateTime(UUID guestId, UUID propertyId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = 1 and b.propertyId = :propertyId and (b.startDateTime < :incomingBookingEndDateTime and b.endDateTime > :incomingBookingStartDateTime)")
    Long overlappingBookingsCount(UUID propertyId, LocalDateTime incomingBookingStartDateTime, LocalDateTime incomingBookingEndDateTime);
}
