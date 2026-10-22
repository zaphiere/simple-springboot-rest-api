package com.simplespringbootrestapi.app.seeder;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.simplespringbootrestapi.app.enums.PropertyStatus;
import com.simplespringbootrestapi.app.model.Project;
import com.simplespringbootrestapi.app.model.Property;
import com.simplespringbootrestapi.app.repository.ProjectRepository;
import com.simplespringbootrestapi.app.repository.PropertyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TableSeeder implements CommandLineRunner{

	private final ProjectRepository projectRepository;
	private final PropertyRepository propertyRepository;
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {
		// Equivalent to Laravel updateOrCreate()
        Project sunset = projectRepository.findByCode("SUNSET")
                .orElseGet(Project::new);

        sunset.setCode("SUNSET");
        sunset.setName("Sunset Residences");
        sunset = projectRepository.save(sunset);

        Project ridge = projectRepository.findByCode("RIDGE")
                .orElseGet(Project::new);

        ridge.setCode("RIDGE");
        ridge.setName("Ridge Estate");
        ridge = projectRepository.save(ridge);

        // Equivalent to Laravel firstOrCreate()
        List<SeedProperty> properties = List.of(
            new SeedProperty(sunset, "Apt 101",
                    PropertyStatus.AVAILABLE, "450000"),
            new SeedProperty(sunset, "Apt 102",
                    PropertyStatus.RESERVED, "460000"),
            new SeedProperty(sunset, "Penthouse A",
                    PropertyStatus.AVAILABLE, "1200000"),
            new SeedProperty(ridge, "Lot 7",
                    PropertyStatus.SOLD, "890000"),
            new SeedProperty(ridge, "Apt 205",
                    PropertyStatus.AVAILABLE, "520000")
        );

        for (SeedProperty row : properties) {

            boolean exists =
                    propertyRepository.existsByProject_IdAndLabel(
                            row.project().getId(),
                            row.label()
                    );

            if (!exists) {
                Property property = new Property();

                property.setProject(row.project());
                property.setLabel(row.label());
                property.setStatus(row.status());
                property.setPrice(new BigDecimal(row.price()));

                propertyRepository.save(property);
            }
        }

        System.out.println("BWP assessment seed data processed.");
		
	}
	
	private record SeedProperty(
            Project project,
            String label,
            PropertyStatus status,
            String price
    ) {}
	
	
}
