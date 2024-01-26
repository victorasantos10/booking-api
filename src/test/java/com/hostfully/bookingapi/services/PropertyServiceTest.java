package com.hostfully.bookingapi.services;

import com.hostfully.bookingapi.models.dto.request.create.PropertyRequestDTO;
import com.hostfully.bookingapi.models.dto.request.update.PropertyUpdateRequestDTO;
import com.hostfully.bookingapi.models.dto.response.PropertyResponseDTO;
import com.hostfully.bookingapi.models.entity.Property;
import com.hostfully.bookingapi.repositories.PropertyRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

    @InjectMocks
    private PropertyService propertyService;

    @Mock
    private PropertyRepository propertyRepository;

    @Test
    public void testGetPropertyByIdWhenValidIdThenReturnPropertyResponseDTO() {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        Property property = new Property();
        property.setId(propertyId);
        property.setName("Test Property");
        property.setAddress("Test Address");
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        // Act
        PropertyResponseDTO result = propertyService.getPropertyById(propertyId);

        // Assert
        assertEquals(propertyId, result.getId());
        assertEquals(property.getName(), result.getName());
        assertEquals(property.getAddress(), result.getAddress());
    }

    @Test
    public void testGetPropertyByIdWhenInvalidIdThenThrowEntityNotFoundException() {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        when(propertyRepository.findById(propertyId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> propertyService.getPropertyById(propertyId));
    }

    @Test
    public void testGetAllProperties() {
        // Arrange
        Property property1 = new Property();
        property1.setId(UUID.randomUUID());
        property1.setName("Test Property 1");
        property1.setAddress("Test Address 1");

        Property property2 = new Property();
        property2.setId(UUID.randomUUID());
        property2.setName("Test Property 2");
        property2.setAddress("Test Address 2");

        ArrayList<Property> properties = new ArrayList<>();
        properties.add(property1);
        properties.add(property2);

        when(propertyRepository.findAll()).thenReturn(properties);

        // Act
        ArrayList<PropertyResponseDTO> result = propertyService.getAllProperties();

        // Assert
        assertEquals(2, result.size());
        assertEquals(property1.toDTO(), result.get(0));
        assertEquals(property2.toDTO(), result.get(1));
    }

    @Test
    public void testUpdateProperty() {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        Property property = new Property();
        property.setId(propertyId);
        property.setName("Test Property");
        property.setAddress("Test Address");

        PropertyUpdateRequestDTO dto = new PropertyUpdateRequestDTO();
        dto.setId(propertyId);
        dto.setName("Updated Property");
        dto.setAddress("Updated Address");

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        // Act
        propertyService.updateProperty(dto);

        // Assert
        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    public void testDeleteProperty() {
        // Arrange
        UUID propertyId = UUID.randomUUID();
        Property property = new Property();
        property.setId(propertyId);
        property.setName("Test Property");
        property.setAddress("Test Address");

        when(propertyRepository.findById(propertyId)).thenReturn(Optional.of(property));

        // Act
        propertyService.deleteProperty(propertyId);

        // Assert
        verify(propertyRepository, times(1)).deleteById(propertyId);
    }

    @Test
    public void testCreateProperty() {
        // Arrange
        PropertyRequestDTO dto = new PropertyRequestDTO();
        dto.setName("Test Property");
        dto.setAddress("Test Address");

        Property property = new Property();
        property.setId(UUID.randomUUID());
        property.setName(dto.getName());
        property.setAddress(dto.getAddress());

        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        // Act
        UUID result = propertyService.createProperty(dto);

        // Assert
        assertEquals(property.getId(), result);
    }
}