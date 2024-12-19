package de.othr.securityproject.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.othr.securityproject.model.Registration;
import de.othr.securityproject.model.Student;
import de.othr.securityproject.repository.RegistrationRepositoryI;
import de.othr.securityproject.repository.StudentRepositoryI;
import de.othr.securityproject.repository.WorkshopRepositoryI;
import de.othr.securityproject.service.RegistrationServiceI;

@Service
public class RegistrationServiceImpl implements RegistrationServiceI{
	
	private StudentRepositoryI  studentRepository;
	private WorkshopRepositoryI  workshopRepository;
	private RegistrationRepositoryI  registrationRepository;
	
	public RegistrationServiceImpl(StudentRepositoryI studentRepository, 
			WorkshopRepositoryI  workshopRepository,
			RegistrationRepositoryI  registrationRepository) {
		
		super();
		this.studentRepository = studentRepository;
		this.workshopRepository = workshopRepository;
		this.registrationRepository = registrationRepository;
	}

	@Override
	public List<Registration> getAllRegistrations() {
		// TODO Auto-generated method stub
		return (List<Registration>) registrationRepository.findAll();
	}

	@Override
	public Registration saveRegistration(Registration registration) {
		// TODO Auto-generated method stub
		return registrationRepository.save(registration);
	}

	@Override
	public Optional<Registration> getRegistrationById(Long id) {
		// TODO Auto-generated method stub
		return registrationRepository.findById(id);
	}

	@Override
	public Registration updateRegistration(Registration registration) {
		// TODO Auto-generated method stub
		return registrationRepository.save(registration);
	}

	@Override
	public void delete(Registration registration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Registration> findRegistrationsByStudent(Student student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Registration> findRegistrationsByDate(LocalDateTime date) {
		// TODO Auto-generated method stub
		return null;
	}

}
