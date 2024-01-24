package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.Block;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BlockRepository extends CrudRepository<Block, UUID> {
}
