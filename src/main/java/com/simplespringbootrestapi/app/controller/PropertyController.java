package com.simplespringbootrestapi.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.simplespringbootrestapi.app.dto.PropertyRequest;
import com.simplespringbootrestapi.app.dto.PropertyResponse;
import com.simplespringbootrestapi.app.model.Property;
import com.simplespringbootrestapi.app.service.PropertyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PropertyController {
	
	@Autowired
	private PropertyService service;
	
	@GetMapping("/properties")
	public ResponseEntity<List<PropertyResponse>> list(
			@RequestParam(required = false, name = "project_id")
			Integer project_id
	) {
		
		List<PropertyResponse> list;
		
		list = project_id == null ? 
				service.getList() :
				service.getListByProjectId(project_id);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/properties/{id}")
	public ResponseEntity<PropertyResponse> getById(@PathVariable int id) {
		
		PropertyResponse response = service.getById(id);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/properties")
	public ResponseEntity<PropertyResponse> create(@Valid @RequestBody PropertyRequest request) {
		PropertyResponse response = service.add(request);
		
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/properties/{id}")
	public ResponseEntity<PropertyResponse> update(
			@PathVariable int id,
			@Valid @RequestBody PropertyRequest request
	) {
		PropertyResponse response = service.update(id, request);
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/properties/{id}")
	public ResponseEntity<String> delete(@PathVariable int id) {
		Property property = service.findProperty(id);
		
		if (property != null) {
			service.delete(property);
		}
		
		return new ResponseEntity<>("Property Deleted", HttpStatus.OK);
	}
	
}
