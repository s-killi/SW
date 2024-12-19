package de.othr.securityproject.repository;


import java.util.List;

import de.othr.securityproject.model.Course;
import de.othr.securityproject.model.Student;



public interface StudentRepositoryI extends MyBaseRepository<Student, Long>{

	List<Student> findByNameContainingIgnoreCase (String name);

	
}
