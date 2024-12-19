package de.othr.im.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.othr.im.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	
	@Query("select p from Professor p where p.user.id=:iduser")
	Optional<Professor> findProfessorByIdUser(Long iduser); 
}
