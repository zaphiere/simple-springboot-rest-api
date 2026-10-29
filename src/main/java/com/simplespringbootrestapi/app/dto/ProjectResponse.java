package com.simplespringbootrestapi.app.dto;

public record ProjectResponse(
	int id,
	String name,
	String code,
	long propertyCount
) {
	
}
