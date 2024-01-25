package com.hostfully.bookingapi.repositories;

import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PropertyTeamMemberRepository extends JpaRepository<PropertyTeamMember, UUID> {
}
