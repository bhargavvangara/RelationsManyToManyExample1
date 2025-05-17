package com.jpademo.relations.manytomany.demo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.jpademo.relations.manytomany.demo.entities.Programmer;
import com.jpademo.relations.manytomany.demo.entities.Project;
import com.jpademo.relations.manytomany.demo.repo.ProgrammerRepo;
import com.jpademo.relations.manytomany.demo.repo.ProjectRepo;

@SpringBootTest
class RelationsManyToManyExample1ApplicationTests {
	@Autowired
	private ProgrammerRepo programmerRepo;

	@Autowired
	private ProjectRepo projectRepo;

	@Test
	@Transactional
	void saveProgrammerAndProjectManually() {
		// Create a new programmer
		Programmer programmer = new Programmer();
		programmer.setName("John Doe");
		programmer.setSalary(100000);

		// Create a new project
		Project project = new Project();
		project.setName("Project A");
		Project project1 = new Project();
		project1.setName("Project B");
		Iterable<Project> savedProjects = projectRepo.saveAll(List.of(project, project1));
		Set<Project> projects = new HashSet<>();
		savedProjects.forEach(projects::add);
		// Add the project to the programmer
		programmer.setProjects(projects);

		// Save the programmer and project
		programmerRepo.save(programmer);

		System.out.println("Programmer and Project saved successfully!");
	}
	
	//Now updating the programmer entity to cascade the project entity
	@Test
	void saveProgrammerAndProjectCascade() {
		// Create a new programmer
		Programmer programmer = new Programmer();
		programmer.setName("Jane Doe");
		programmer.setSalary(120000);

		// Create a new project
		Project project = new Project();
		project.setName("Project C");
		Project project1 = new Project();
		project1.setName("Project D");
		Set<Project> projects = new HashSet<>();
		projects.add(project);
		projects.add(project1);
		
		programmer.setProjects(projects);

		// Save the programmer and project
		programmerRepo.save(programmer);

		System.out.println("Programmer and Project saved successfully!");
		
	}
	
	// Now updating the programmer to add new project
	@Test
	@Transactional
	@Rollback(false)
	void updateProgrammerAndProject() {
		// Find the programmer by ID
		Programmer programmer = programmerRepo.findById(1).orElse(null);
		if (programmer != null) {
			// Create a new project
			Project project = new Project();
			project.setName("Project E");
			
			programmer.getProjects().add(project);

			// Save the updated programmer
			programmerRepo.save(programmer);

			System.out.println("Programmer and Project updated successfully!");
		} else {
			System.out.println("Programmer not found!");
		}
		
	}
	
	// Save only programmer (bench)
	@Test
	@Transactional
	@Rollback(false)
	void saveProgrammer() {
		// Create a new programmer
		Programmer programmer = new Programmer();
		programmer.setName("Robert Doe");
		programmer.setSalary(100000);

		// Save the programmer
		programmerRepo.save(programmer);

		System.out.println("Programmer saved successfully!");
		
	}
	
	//Map the project to the programmer
	@Test
	@Transactional
	@Rollback(false)
	void mapProjectToProgrammer() {
		// Find the programmer by ID
		Programmer programmer = programmerRepo.findById(4).orElse(null);
		if (programmer != null) {
			// Find the project by ID
			Project project = projectRepo.findById(1).orElse(null);
			if (project != null) {
				// Add the project to the programmer
				programmer.getProjects().add(project);

				// Save the updated programmer
				programmerRepo.save(programmer);

				System.out.println("Project mapped to Programmer successfully!");
			} else {
				System.out.println("Project not found!");
			}
		} else {
			System.out.println("Programmer not found!");
		}
		
	}
	//Remove the project from the programmer
	@Test
	@Transactional
	@Rollback(false)
	void removeProjectFromProgrammer() {
		// Find the programmer by ID
		Programmer programmer = programmerRepo.findById(1).orElse(null);
		if (programmer != null) {
			// Find the project by ID
			Project project = projectRepo.findById(2).orElse(null);
			if (project != null) {
				// Remove the project from the programmer
				programmer.getProjects().remove(project);

				// Save the updated programmer
				programmerRepo.save(programmer);

				System.out.println("Project removed from Programmer successfully!");
			} else {
				System.out.println("Project not found!");
			}
		} else {
			System.out.println("Programmer not found!");
		}
		
	}
	//Problem with cascade All , delete programmer will delete project
	@Test
	@Transactional
	@Rollback(false)
	void deleteProgrammer() {
		// Find the programmer by ID
		Programmer programmer = programmerRepo.findById(2).orElse(null);
		if (programmer != null) {
			// Delete the programmer
			programmerRepo.delete(programmer);

			System.out.println("Programmer deleted successfully!, It also delete the project");
		} else {
			System.out.println("Programmer not found!");
		}
		
	}
	// Fetch records in inverse direction
	@Test
	@Transactional
	void findProgrammerByProject() {
		// Find the project by ID
		Project project = projectRepo.findById(1).orElse(null);
		if (project != null) {
			System.out.println("Project Name: " + project.getName());
			System.out.println("Programmers working on this project:");
			for (Programmer programmer : project.getProgrammers()) {
				System.out.println(" - " + programmer.getName());
			}
		} else {
			System.out.println("Project not found!");
		}
		
	}
}
