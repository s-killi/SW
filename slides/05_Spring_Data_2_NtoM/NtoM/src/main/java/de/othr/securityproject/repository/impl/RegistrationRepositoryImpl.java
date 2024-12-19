package de.othr.securityproject.repository.impl;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import de.othr.securityproject.model.Registration;
import de.othr.securityproject.repository.RegistrationRepositoryI;

@Repository
public interface RegistrationRepositoryImpl extends RegistrationRepositoryI, CrudRepository<Registration, Long> {

	
	
	
}
