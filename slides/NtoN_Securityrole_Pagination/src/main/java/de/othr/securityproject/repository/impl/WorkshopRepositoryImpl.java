package de.othr.securityproject.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import de.othr.securityproject.model.Course;
import de.othr.securityproject.model.Workshop;
import de.othr.securityproject.repository.WorkshopRepositoryI;

@Repository
public interface WorkshopRepositoryImpl extends CrudRepository<Workshop, Long>, WorkshopRepositoryI {

	
	List<Workshop> findByDescriptionContainingIgnoreCase (String description);
	
	@Query(value = "from Workshop w where w.date BETWEEN :startDate AND :endDate")
	List<Workshop> findByDate(@Param("startDate") LocalDateTime startDate,
            				  @Param("endDate") LocalDateTime endDate);
	
}
