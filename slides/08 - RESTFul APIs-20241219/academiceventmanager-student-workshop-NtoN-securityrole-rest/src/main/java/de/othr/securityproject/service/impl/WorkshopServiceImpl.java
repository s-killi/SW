package de.othr.securityproject.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import de.othr.securityproject.model.Workshop;
import de.othr.securityproject.repository.WorkshopRepositoryI;
import de.othr.securityproject.service.WorkshopServiceI;

@Service
public class WorkshopServiceImpl implements WorkshopServiceI{
	
	
	private WorkshopRepositoryI  workshopRepository;
	
	
	public WorkshopServiceImpl (WorkshopRepositoryI  workshopRepository) {
		
		this.workshopRepository=workshopRepository;
	}


	@Override
	public List<Workshop> getAllWorkshops() {
		// TODO Auto-generated method stub
		return (List<Workshop>) workshopRepository.findAll();
	}


	@Override
	public List<Workshop> findWorkshopsByDescription(String description) {
		// TODO Auto-generated method stub
		return workshopRepository.findByDescriptionContainingIgnoreCase(description);
	}


	@Override
	public Workshop saveWorkshop(Workshop workshop) {
		// TODO Auto-generated method stub
		return workshopRepository.save(workshop);
	}


	@Override
	public Workshop getWorkshopById(Long id) {
		// TODO Auto-generated method stub
		Optional<Workshop> workshopOpt = workshopRepository.findById(id);
		if (workshopOpt.isPresent()) {
			return workshopOpt.get();
		}
		else {
		 
		 return null;
		}
	}


	@Override
	public Workshop updateWorkshop(Workshop workshop) {
		// TODO Auto-generated method stub
		return workshopRepository.save(workshop);
	}


	@Override
	public void delete(Workshop workshop) {
		// TODO Auto-generated method stub
		workshopRepository.delete(workshop);
	}


	
}
