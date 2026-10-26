package com.simplespringbootrestapi.app.mapper;

import org.springframework.stereotype.Component;

import com.simplespringbootrestapi.app.dto.ProjectResponse;
import com.simplespringbootrestapi.app.model.Project;

@Component
public class ProjectMapper {

	public ProjectResponse toResponse(
			Project project,
			long propertyCount
	) {
		return new ProjectResponse(
				project.getId(),
				project.getName(),
				project.getCode(),
				propertyCount
		);
	}
}
