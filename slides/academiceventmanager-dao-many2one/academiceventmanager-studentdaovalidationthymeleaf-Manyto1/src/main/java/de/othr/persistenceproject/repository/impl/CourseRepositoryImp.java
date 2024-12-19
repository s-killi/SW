package de.othr.persistenceproject.repository.impl;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.othr.persistenceproject.model.Course;

import de.othr.persistenceproject.repository.CourseRepositoryI;


public interface CourseRepositoryImp extends  CourseRepositoryI, CrudRepository<Course, Long>{
	
	List<Course> findByDescriptionContainingIgnoreCase (String description);
	
	

}
