package com.simplespringbootrestapi.app.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.simplespringbootrestapi.app.dto.PropertyRequest;
import com.simplespringbootrestapi.app.dto.PropertyResponse;
import com.simplespringbootrestapi.app.mapper.PropertyMapper;
import com.simplespringbootrestapi.app.model.Project;
import com.simplespringbootrestapi.app.model.Property;
import com.simplespringbootrestapi.app.repository.ProjectRepository;
import com.simplespringbootrestapi.app.repository.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	PropertyRepository repo;
	
	@Autowired
	ProjectRepository projectRepo;
	
	@Autowired
	PropertyMapper mapper;
	
	public List<PropertyResponse> getList() {
		return repo.findAll()
				.stream()
				.map(mapper::toResponse)
				.toList();
	}

	public List<PropertyResponse> getListByProjectId(Integer projectId) {
		return repo.findByProject_Id(projectId)
				.stream()
				.map(mapper::toResponse)
				.toList();
	}

	public PropertyResponse getById(int id) {
		Property property = repo.findById(id)
				.orElseThrow(() ->
					new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Property not found with id: " + id
					)
				);
		
		return mapper.toResponse(property);
	}

	public PropertyResponse add(PropertyRequest request) {
		Property property = new Property();
		Project project = findProject(request.projectId());
		Property saveProperty = saveProperty(project, property, request);
		
		return mapper.toResponse(saveProperty);
	}

	public PropertyResponse update(int id, PropertyRequest request) {
		Property property = findProperty(id);
		
		Project project = findProject(request.projectId());
		
		Property saveProperty = saveProperty(project, property, request);
		
		return mapper.toResponse(saveProperty);
	}

	public Property findProperty(int id) {
		return repo.findById(id)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.NOT_FOUND,
						"Property Not Found"
				));
	}

	private Property saveProperty(Project project, Property property, PropertyRequest request) {
		property.setProject(project);
		property.setLabel(request.label());
		property.setStatus(request.status());
		property.setPrice(request.price());
		property.setTimestamps(LocalDateTime.now());
		
		return repo.save(property);
	}

	private Project findProject(Integer projectId) {
		return projectRepo.findById(projectId)
				.orElseThrow(() -> new ResponseStatusException(
						HttpStatus.BAD_REQUEST,
						"Project not found"
				));
	}

	public void delete(Property property) {
		repo.delete(property);
	}

}
