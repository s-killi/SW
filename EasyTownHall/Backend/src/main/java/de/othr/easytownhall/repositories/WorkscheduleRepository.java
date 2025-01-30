package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.Workschedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkscheduleRepository extends JpaRepository<Workschedule, Long> {

    @Query("SELECT w FROM Workschedule w " +
            "JOIN FETCH w.department d " +
            "WHERE w.date = :date AND d.id = :departmentId")
    List<Workschedule> findAllByDateAndDepartment(@Param("date") LocalDate date, @Param("departmentId") Long departmentId);

    @Query("SELECT w FROM Workschedule w WHERE w.department.id = :departmentId ORDER BY w.id ASC")
    Workschedule findFirstByDepartmentId(@Param("departmentId") Long departmentId);

    @Query("SELECT w FROM Workschedule w WHERE w.department.id = :departmentId AND w.date = :date ORDER BY w.id ASC")
    Workschedule findFirstByDepartmentIdAndDate(@Param("departmentId") Long departmentId, @Param("date") LocalDate date);

    @Query("SELECT w FROM Workschedule w JOIN w.timeSlots t WHERE t.id = :timeSlotId")
    Optional<Workschedule> findByTimeSlotId(@Param("timeSlotId") Long timeSlotId);
}
