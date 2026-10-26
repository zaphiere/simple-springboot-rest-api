package com.simplespringbootrestapi.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.simplespringbootrestapi.app.dto.PropertyResponse;
import com.simplespringbootrestapi.app.mapper.PropertyMapper;
import com.simplespringbootrestapi.app.model.Property;
import com.simplespringbootrestapi.app.repository.PropertyRepository;

@Service
public class PropertyService {

	@Autowired
	PropertyRepository repo;
	
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

}
