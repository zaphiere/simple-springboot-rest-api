package com.simplespringbootrestapi.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.simplespringbootrestapi.app.dto.PropertyRequest;
import com.simplespringbootrestapi.app.dto.PropertyResponse;
import com.simplespringbootrestapi.app.enums.PropertyStatus;
import com.simplespringbootrestapi.app.mapper.PropertyMapper;
import com.simplespringbootrestapi.app.model.Project;
import com.simplespringbootrestapi.app.model.Property;
import com.simplespringbootrestapi.app.repository.ProjectRepository;
import com.simplespringbootrestapi.app.repository.PropertyRepository;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {

	@Mock
	private PropertyRepository propertyRepo;
	
	@Mock
	private ProjectRepository projectRepo;
	
	@Mock
	private PropertyMapper mapper;
	
	@InjectMocks
	private PropertyService service;
	
	@Test
	void add_shouldSaveProperty_whenProjectExists() {
		// Prepare test data
		Project project = new Project();
		project.setId(1);
		
		PropertyRequest request = new PropertyRequest(1, "Unit A1", PropertyStatus.AVAILABLE, new BigDecimal("1000.00"));
		PropertyResponse expected = new PropertyResponse(10, 1, "Unit A1", PropertyStatus.AVAILABLE, new BigDecimal("1000.00"));
		
		// Mock Project
		when(projectRepo.findById(1)).thenReturn(Optional.of(project));
		
		// Mock Saving property successfully
		when(propertyRepo.save(any(Property.class)))
        .thenAnswer(invocation -> {
            Property property = invocation.getArgument(0);
            property.setId(10);
            return property;
        });
		
		// Mock mapper converting entity to response
		when(mapper.toResponse(any(Property.class)))
			.thenReturn(expected);
		
		// Act: call actual service method
		PropertyResponse actual = service.add(request);
		
		// Assert: verify result
		assertNotNull(actual);
		assertEquals(expected, actual);
		
		// Verify: expected methods are called
		verify(projectRepo).findById(1);
		verify(propertyRepo).save(any(Property.class));
		verify(mapper).toResponse(any(Property.class));
	}
	
	@Test
	void add_shouldThrowException_whenProjectDoesNotExist() {
	    // Arrange
	    PropertyRequest request = new PropertyRequest(
	        99,
	        "Unit A1",
	        PropertyStatus.AVAILABLE,
	        new BigDecimal("100000.00")
	    );

	    when(projectRepo.findById(99))
	        .thenReturn(Optional.empty());

	    // Act
	    ResponseStatusException exception = assertThrows(
	        ResponseStatusException.class,
	        () -> service.add(request)
	    );

	    // Assert
	    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());

	    verify(projectRepo).findById(99);
	    verify(propertyRepo, never()).save(any(Property.class));
	}
}
