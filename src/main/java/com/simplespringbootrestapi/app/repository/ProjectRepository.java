package com.simplespringbootrestapi.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.simplespringbootrestapi.app.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer>{

	Optional<Project> findByCode(String code);
}
