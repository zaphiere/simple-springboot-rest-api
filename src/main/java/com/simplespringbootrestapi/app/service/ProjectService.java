package com.simplespringbootrestapi.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simplespringbootrestapi.app.dto.ProjectResponse;
import com.simplespringbootrestapi.app.mapper.ProjectMapper;
import com.simplespringbootrestapi.app.repository.ProjectRepository;
import com.simplespringbootrestapi.app.repository.PropertyRepository;

@Service
public class ProjectService {
	
	@Autowired
	private ProjectRepository projRepo;
	
	@Autowired
	private PropertyRepository propRepo;
	
	@Autowired
	private ProjectMapper mapper;

	public List<ProjectResponse> getList() {
		return projRepo.findAll()
				.stream()
				.map(project -> mapper.toResponse(
						project, 
						propRepo.countByProject_Id(project.getId()
					)
				))
				.toList();
	}

}
