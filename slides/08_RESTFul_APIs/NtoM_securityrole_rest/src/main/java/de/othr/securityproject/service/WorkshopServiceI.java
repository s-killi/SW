package de.othr.securityproject.service;

import java.util.List;


import de.othr.securityproject.model.Workshop;

public interface WorkshopServiceI {
	
	List<Workshop> getAllWorkshops();
	
	List<Workshop> findWorkshopsByDescription(String description);
	
	Workshop saveWorkshop(Workshop workshop);
	
	Workshop getWorkshopById(Long id);
	
	Workshop updateWorkshop(Workshop workshop);
	
	void delete(Workshop workshop);
	

}
