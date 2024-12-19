package de.othr.securityproject.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.othr.securityproject.model.Registration;
import de.othr.securityproject.model.Student;

@Service
public interface RegistrationServiceI {
	
	List<Registration> getAllRegistrations();
	
	Registration saveRegistration(Registration registration);
	
	Optional <Registration> getRegistrationById(Long id);
	
	Registration updateRegistration(Registration registration);
	
	void delete(Registration registration);
	
	public List<Registration> findRegistrationsByStudent(Student student) ;
	
	public List<Registration> findRegistrationsByDate(LocalDateTime date) ;


}
