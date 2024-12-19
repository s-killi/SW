package de.othr.securityproject.controller.rest;

import java.util.ArrayList;
import java.util.List;


import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import de.othr.securityproject.model.Workshop;

import de.othr.securityproject.service.WorkshopServiceI;

@RestController
public class WorkshopRestController {

	
	
	
	private final WorkshopServiceI workshopService;

	WorkshopRestController(WorkshopServiceI workshopService) {
		this.workshopService =workshopService;
	}
	
	//create
	@PostMapping("/api/workshops")
	 public ResponseEntity<EntityModel<Workshop>> post(@RequestBody Workshop workshopFromRequest) {
		
		System.out.println("workshop.description="+workshopFromRequest.getDescription());
		
		Workshop workshop = workshopService.saveWorkshop(workshopFromRequest);
		EntityModel<Workshop> entityModel = EntityModel.of(workshop);
		Link workshopLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkshopRestController.class)
				  .getWorkshopByID(workshop.getId())).withSelfRel();
		
		
		entityModel.add(workshopLink);
		
		return new ResponseEntity<>(entityModel, HttpStatus.CREATED);
	    // POST
	  }

	//update
	@PutMapping("/api/workshops/{id}")
	 public ResponseEntity<EntityModel<Workshop>> put (@RequestBody Workshop workshopFromRequest, @PathVariable long id) {
		
		System.out.println("workshop.description="+workshopFromRequest.getDescription());
		
		Workshop workshopDB = workshopService.getWorkshopById(id);
		
		if (workshopDB==null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			
			
		}
	
		workshopDB.setDescription(workshopFromRequest.getDescription());
		workshopDB.setDate(workshopFromRequest.getDate());
		
		
		Workshop workshopUpdated = workshopService.saveWorkshop(workshopDB);
		EntityModel<Workshop> entityModel = EntityModel.of(workshopUpdated);
		Link workshopLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkshopRestController.class)
				  .getWorkshopByID(workshopUpdated.getId())).withSelfRel();
		
		
		entityModel.add(workshopLink);
		
		return new ResponseEntity<>(entityModel, HttpStatus.OK);
	    // PUT
	  }

	
	//read
	@GetMapping("/api/workshops/{id}")
	ResponseEntity<EntityModel<Workshop>> getWorkshopByID(@PathVariable long id) {

		Workshop workshop = workshopService.getWorkshopById(id);
		
		if (workshop==null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		EntityModel<Workshop> entityModel = EntityModel.of(workshop);
		
		Link workshopLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkshopRestController.class)
				  .getWorkshopByID(workshop.getId())).withSelfRel();
	
		entityModel.add(workshopLink);
		
		return new ResponseEntity<>(entityModel, HttpStatus.OK);
		
	}
	
	//read or return all
	@GetMapping("/api/workshops")
	public ResponseEntity <CollectionModel<EntityModel<Workshop>>> all(@RequestParam(required = false) String description) {
	
		List <Workshop> workshops;
		
		if (description != null) {
            workshops = workshopService.findWorkshopsByDescription(description);
        } else {
            workshops = workshopService.getAllWorkshops();
        }
	
	
	 
		if (workshops.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
	
		
				 
		List<EntityModel<Workshop>> workshopModels = new ArrayList() ;
		 
		for (Workshop workshop : workshops) {
			 
			 EntityModel<Workshop> entityModel = EntityModel.of(workshop);
			 
			 Link workshopLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkshopRestController.class)
					  .getWorkshopByID(workshop.getId())).withSelfRel();
			 
			 entityModel.add(workshopLink);
			 workshopModels.add(entityModel);
			 			 
			 
		 }
		 
		 Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(WorkshopRestController.class)
			      .all(description)).withSelfRel();
		 
		 return new ResponseEntity<>( CollectionModel.of(workshopModels, link), HttpStatus.OK);
		 
		
	}
	
	
		
	//delete
	@DeleteMapping("/api/workshops/{id}")
	ResponseEntity<?> deleteWorkshop(@PathVariable Long id) {

		Workshop workshop = workshopService.getWorkshopById(id);
		
		if (workshop==null) {
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	
		workshopService.delete(workshop);

	  return ResponseEntity.noContent().build();
	  
	}
}