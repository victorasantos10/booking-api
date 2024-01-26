package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.enums.TeamMemberType;
import com.hostfully.bookingapi.models.dto.request.create.PropertyTeamMemberRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.PropertyTeamMemberUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyTeamMemberResponseDTO;
import com.hostfully.bookingapi.models.entity.Booking;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.models.entity.PropertyTeamMember;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import com.hostfully.bookingapi.repositories.PropertyTeamMemberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyTeamMemberServiceTest {

    @Mock
    private PropertyTeamMemberRepository propertyTeamMemberRepository;

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyTeamMemberService propertyTeamMemberService;

    private UUID testUUID;
    private PropertyTeamMember testTeamMember;
    private Property testProperty;

    @BeforeEach
    public void setUp() {
        testUUID = UUID.randomUUID();
        testProperty = new Property();
        testProperty.setId(UUID.randomUUID());
        testTeamMember = new PropertyTeamMember();
        testTeamMember.setId(UUID.randomUUID());
        testTeamMember.setProperty(testProperty);
        testTeamMember.setType(1);
    }

    @Test
    public void testValidateTeamMemberWhenTeamMemberExistsThenNoException() {
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.of(testTeamMember));

        propertyTeamMemberService.validateTeamMember(testUUID);
    }

    @Test
    public void testValidateTeamMemberWhenTeamMemberDoesNotExistThenEntityNotFoundException() {
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> propertyTeamMemberService.validateTeamMember(testUUID));
    }

    @Test
    public void testGetTeamMemberWhenTeamMemberExistsThenReturnTeamMember() {
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.of(testTeamMember));

        PropertyTeamMemberResponseDTO responseDTO = propertyTeamMemberService.getTeamMember(testUUID);

        assertNotNull(responseDTO);
    }

    @Test
    public void testGetTeamMemberWhenTeamMemberDoesNotExistThenEntityNotFoundException() {
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> propertyTeamMemberService.getTeamMember(testUUID));
    }

    @Test
    public void testGetAllTeamMembersWhenTeamMembersExistThenReturnAllTeamMembers() {
        ArrayList<PropertyTeamMember> teamMembers = new ArrayList<>();
        teamMembers.add(testTeamMember);
        when(propertyTeamMemberRepository.findAll()).thenReturn(teamMembers);

        ArrayList<PropertyTeamMemberResponseDTO> responseDTOs = propertyTeamMemberService.getAllTeamMembers();

        assertFalse(responseDTOs.isEmpty());
    }

    @Test
    public void testUpdatePropertyTeamMemberWhenTeamMemberExistsThenNoException() {
        PropertyTeamMemberUpdateRequestDTO dto = new PropertyTeamMemberUpdateRequestDTO();
        dto.setId(testUUID);
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.of(testTeamMember));

        propertyTeamMemberService.updatePropertyTeamMember(dto);

        verify(propertyTeamMemberRepository, times(1)).save(any(PropertyTeamMember.class));
    }

    @Test
    public void testUpdatePropertyTeamMemberWhenTeamMemberDoesNotExistThenEntityNotFoundException() {
        PropertyTeamMemberUpdateRequestDTO dto = new PropertyTeamMemberUpdateRequestDTO();
        dto.setId(testUUID);
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> propertyTeamMemberService.updatePropertyTeamMember(dto));
    }

    @Test
    public void testDeletePropertyTeamMemberWhenTeamMemberExistsThenNoException() {
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.of(testTeamMember));

        propertyTeamMemberService.deletePropertyTeamMember(testUUID);

        verify(propertyTeamMemberRepository, times(1)).deleteById(testUUID);
    }

    @Test
    public void testDeletePropertyTeamMemberWhenTeamMemberDoesNotExistThenEntityNotFoundException() {
        when(propertyTeamMemberRepository.findById(testUUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> propertyTeamMemberService.deletePropertyTeamMember(testUUID));
    }

    @Test
    public void testCreatePropertyTeamMemberWhenPropertyExistsThenReturnUUID() {
        PropertyTeamMemberRequestDTO dto = new PropertyTeamMemberRequestDTO();
        dto.setType(TeamMemberType.MANAGER);
        dto.setPropertyId(testUUID);
        when(propertyRepository.findById(testUUID)).thenReturn(Optional.of(testProperty));

        PropertyTeamMember savedEntity = new PropertyTeamMember();
        savedEntity.setId(UUID.randomUUID());

        when(propertyTeamMemberRepository.save(any(PropertyTeamMember.class))).thenReturn(savedEntity);

        UUID resultUUID = propertyTeamMemberService.createPropertyTeamMember(dto);

        assertNotNull(resultUUID);
    }

    @Test
    public void testCreatePropertyTeamMemberWhenPropertyDoesNotExistThenEntityNotFoundException() {
        PropertyTeamMemberRequestDTO dto = new PropertyTeamMemberRequestDTO();
        dto.setPropertyId(testUUID);
        when(propertyRepository.findById(testUUID)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> propertyTeamMemberService.createPropertyTeamMember(dto));
    }
}