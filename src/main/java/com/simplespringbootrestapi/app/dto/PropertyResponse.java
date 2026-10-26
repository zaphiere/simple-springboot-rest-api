package com.simplespringbootrestapi.app.dto;

import java.math.BigDecimal;

import com.simplespringbootrestapi.app.enums.PropertyStatus;

public record PropertyResponse(
		int id,
		int projectId,
		String label,
		PropertyStatus status,
		BigDecimal price
) {
	
}
