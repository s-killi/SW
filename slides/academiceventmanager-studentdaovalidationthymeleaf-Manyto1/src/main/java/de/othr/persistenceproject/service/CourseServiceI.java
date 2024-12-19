package de.othr.persistenceproject.service;

import java.util.List;
import java.util.Optional;

import de.othr.persistenceproject.model.Course;


public interface CourseServiceI {
	
	List<Course> getAllCourses();
	
	Course saveCourse(Course course);
	
	Optional <Course> getCourseById(Long id);
	
	Course updateCourse(Course course);
	
	void delete(Course course);
	
	public List<Course> findCoursesByDescription(String description) ;


}
