package com.simplespringbootrestapi.app.dto;

import java.math.BigDecimal;

import com.simplespringbootrestapi.app.enums.PropertyStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record PropertyRequest(
		
		@Positive(message = "Project ID must be positive")
		@NotNull(message = "Project ID is Required")
		Integer projectId,
		
		@NotBlank(message = "Label is required")
		@Size(min = 15, message = "Label must be more than 15 characters")
		String label,
		
		@NotNull(message = "Status is required")
		PropertyStatus status,
		
		@NotNull(message = "Price is required")
		@Positive(message = "Price must be greater than zero")
		BigDecimal price
) {
	
}
