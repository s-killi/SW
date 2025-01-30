package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query("SELECT t FROM TimeSlot t WHERE t.citizen.id = :citizenId")
    List<TimeSlot> findAllByCitizenId(@Param("citizenId") Long citizenId);
}
