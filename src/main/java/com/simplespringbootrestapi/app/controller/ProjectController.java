package com.simplespringbootrestapi.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simplespringbootrestapi.app.dto.ProjectResponse;
import com.simplespringbootrestapi.app.service.ProjectService;

@RestController
@RequestMapping("/api")
public class ProjectController {
	
	@Autowired
	private ProjectService service;
	
	@GetMapping("/projects")
	public ResponseEntity<List<ProjectResponse>> list() {
		List<ProjectResponse> list = service.getList();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
