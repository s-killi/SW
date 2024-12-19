package de.othr.securityproject.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.othr.securityproject.model.Workshop;

public interface WorkshopRepositoryI extends MyBaseRepository<Workshop, Long> {

	List<Workshop> findByDate (LocalDateTime startDate, LocalDateTime endDate);
	List<Workshop> findByDescriptionContainingIgnoreCase (String description);
}
