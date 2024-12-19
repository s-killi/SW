package de.othr.securityproject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.othr.securityproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findUserByLogin(String login); 
	Optional<User> findByEmail(String email);
	
}
