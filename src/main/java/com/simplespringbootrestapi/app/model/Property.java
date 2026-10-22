package com.simplespringbootrestapi.app.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.simplespringbootrestapi.app.enums.PropertyStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Property {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "project_id", nullable = false)
	private Project project;
	
	private String label;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private PropertyStatus status;
	
	@Column(nullable = false, precision = 12, scale = 2)
	private BigDecimal price;
	
	private LocalDateTime timestamps;
}
