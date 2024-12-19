package de.othr.securityproject.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import de.othr.securityproject.model.Student;

public interface StudentServiceI {
	
	Page<Student> getAllStudents(String name, Pageable pageable);
	
	List<Student> findStudentsByName(String name);
	
	Student saveStudent(Student student);
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student);
	
	void delete(Student student);
	
	
}
