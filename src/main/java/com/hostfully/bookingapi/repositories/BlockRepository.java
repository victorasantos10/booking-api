package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.Block;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface BlockRepository extends CrudRepository<Block, UUID> {
    @Query("SELECT b FROM Block b WHERE b.isActive = :isActive and b.propertyId = :propertyId and (b.startDate <= :incomingEndDate and b.endDate >= :incomingStartDate)")
    List<Block> findByPropertyIdAndIsActiveAndStartDateAndEndDate(UUID propertyId, boolean isActive, LocalDate incomingStartDate, LocalDate incomingEndDate);
}
