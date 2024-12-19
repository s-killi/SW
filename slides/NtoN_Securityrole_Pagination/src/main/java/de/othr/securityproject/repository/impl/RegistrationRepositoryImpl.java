package de.othr.securityproject.repository.impl;

import org.springframework.data.repository.CrudRepository;

import de.othr.securityproject.model.Registration;
import de.othr.securityproject.repository.RegistrationRepositoryI;

public interface RegistrationRepositoryImpl extends RegistrationRepositoryI, CrudRepository<Registration, Long> {

	
	
	
}
