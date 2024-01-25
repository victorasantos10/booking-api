package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Booking b set b.status = :status where b.id in (:idList)")
    void updateBookingsStatus(ArrayList<UUID> idList, Integer status);

    @Query("SELECT b FROM Booking b WHERE b.status in (1,2) and b.propertyId = :propertyId and (b.startDateTime < :incomingEndDate and b.endDateTime > :incomingStartDate)")
    ArrayList<Booking> findActiveOrRebookedBookingsWithinDate(UUID propertyId, LocalDateTime incomingStartDate, LocalDateTime incomingEndDate);
}
