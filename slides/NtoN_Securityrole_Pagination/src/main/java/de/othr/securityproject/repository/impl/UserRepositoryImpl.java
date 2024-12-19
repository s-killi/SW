package de.othr.securityproject.repository.impl;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.othr.securityproject.model.User;
import de.othr.securityproject.repository.UserRepositoryI;

@Repository
public interface UserRepositoryImpl extends UserRepositoryI, CrudRepository<User, Long>{

	
	
	
}
