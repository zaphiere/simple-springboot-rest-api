package com.simplespringbootrestapi.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplespringbootrestapi.app.model.Property;


public interface PropertyRepository extends JpaRepository<Property, Integer> {
	boolean existsByProject_IdAndLabel(int projectId, String label);
	
	long countByProject_Id(int projectId);

	List<Property> findByProject_Id(Integer projectId);
}
