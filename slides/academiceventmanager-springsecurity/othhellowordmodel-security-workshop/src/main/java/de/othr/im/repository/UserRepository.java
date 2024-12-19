package de.othr.im.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import de.othr.im.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	
	Optional<User> findUserByLogin(String login); 
}
