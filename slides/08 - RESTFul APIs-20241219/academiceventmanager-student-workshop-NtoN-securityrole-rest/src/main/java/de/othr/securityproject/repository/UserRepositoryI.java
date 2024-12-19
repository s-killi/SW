package de.othr.securityproject.repository;

import java.util.Optional;



import de.othr.securityproject.model.User;

public interface UserRepositoryI extends MyBaseRepository<User, Long> {
	
	
	Optional<User> findByLoginIgnoreCase(String login);

}
