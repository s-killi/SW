package de.othr.persistenceproject.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import de.othr.persistenceproject.model.Course;
import de.othr.persistenceproject.model.Student;
import de.othr.persistenceproject.repository.CourseRepositoryI;
import de.othr.persistenceproject.repository.StudentRepositoryI;
import de.othr.persistenceproject.repository.impl.StudentRepositoryImp;
import de.othr.persistenceproject.service.StudentServiceI;

@Service
public class StudentServiceImpl implements StudentServiceI{

	private StudentRepositoryI  studentRepository;
	
	
	public StudentServiceImpl(StudentRepositoryI studentRepository) {
		super();
		this.studentRepository = studentRepository;
		
	} 
	
	@Override
	public List<Student> getAllStudents() {
		// TODO Auto-generated method stub
		return (List<Student>) studentRepository.findAll();
	}

	@Override
	public Student saveStudent(Student student) {
		// TODO Auto-generated method stub
		
		return studentRepository.save(student);
	}

	@Override
	public Student getStudentById(Long id) {
		// TODO Auto-generated method stub
		return studentRepository.findById(id).get();
	}

	@Override
	public Student updateStudent(Student student) {
		// TODO Auto-generated method stub
		System.out.println(student.getGender()+"***");
		return studentRepository.save(student);
	}

	@Override
	public void delete(Student student) {
		// TODO Auto-generated method stub
		studentRepository.delete(student);	
	}



}
