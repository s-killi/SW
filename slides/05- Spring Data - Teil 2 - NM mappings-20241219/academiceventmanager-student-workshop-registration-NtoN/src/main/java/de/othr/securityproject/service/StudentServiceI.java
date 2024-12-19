package de.othr.securityproject.service;

import java.util.List;

import de.othr.securityproject.model.Course;
import de.othr.securityproject.model.Student;

public interface StudentServiceI {
	
	List<Student> getAllStudents();
	
	List<Student> findStudentsByName(String name);
	
	Student saveStudent(Student student);
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student);
	
	void delete(Student student);
	
	
}
