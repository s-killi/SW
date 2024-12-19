package de.othr.securityproject.repository.impl;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import de.othr.securityproject.model.Course;
import de.othr.securityproject.repository.CourseRepositoryI;


public interface CourseRepositoryImp extends  CourseRepositoryI, CrudRepository<Course, Long>{
	
	List<Course> findByDescriptionContainingIgnoreCase (String description);
	
	

}
