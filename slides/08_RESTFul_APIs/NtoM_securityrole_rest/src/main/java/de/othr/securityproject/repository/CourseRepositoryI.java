package de.othr.securityproject.repository;

import java.util.List;

import de.othr.securityproject.model.Course;

public interface CourseRepositoryI extends MyBaseRepository<Course, Long> {

	
	List<Course> findByDescriptionContainingIgnoreCase (String description);
}
