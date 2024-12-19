package de.othr.persistenceproject.service;

import java.util.List;

import de.othr.persistenceproject.model.Course;
import de.othr.persistenceproject.model.Student;

public interface StudentServiceI {
	
	List<Student> getAllStudents();
	
	Student saveStudent(Student student);
	
	Student getStudentById(Long id);
	
	Student updateStudent(Student student);
	
	void delete(Student student);
	
	
}
