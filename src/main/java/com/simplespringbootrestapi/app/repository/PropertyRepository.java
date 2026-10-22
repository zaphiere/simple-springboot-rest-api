package com.simplespringbootrestapi.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplespringbootrestapi.app.model.Property;


public interface PropertyRepository extends JpaRepository<Property, Integer> {
	boolean existsByProject_IdAndLabel(int projectId, String label);
}
