package de.othr.im.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.othr.im.model.Manager;
import de.othr.im.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	@Query("select s from Student s where s.user.id=:iduser")
	Optional<Student> findStudentByIdUser(Long iduser); 
	
	
}
