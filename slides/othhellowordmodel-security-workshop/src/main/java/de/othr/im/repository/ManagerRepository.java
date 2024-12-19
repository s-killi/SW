package de.othr.im.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import de.othr.im.model.Manager;


@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long>{

	@Query("select m from Manager m where m.user.id=:iduser")
	Optional<Manager> findManagerByIdUser(Long iduser); 
	
}
