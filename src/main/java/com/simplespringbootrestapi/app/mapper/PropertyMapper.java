package com.simplespringbootrestapi.app.mapper;

import org.springframework.stereotype.Component;

import com.simplespringbootrestapi.app.dto.PropertyResponse;
import com.simplespringbootrestapi.app.model.Property;

@Component
public class PropertyMapper {
	
	public PropertyResponse toResponse(Property property) {
		return new PropertyResponse(
				property.getId(),
				property.getProject().getId(),
				property.getLabel(),
				property.getStatus(),
				property.getPrice()
		);
	}
}
