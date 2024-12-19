package de.othr.persistenceproject.repository;

import java.util.List;

import de.othr.persistenceproject.model.Course;

public interface CourseRepositoryI extends MyBaseRepository<Course, Long> {

	
	List<Course> findByDescriptionContainingIgnoreCase (String description);
}
