package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.department.ServiceWorker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServiceWorkerRepository extends JpaRepository<ServiceWorker, Long>{
    @Query("SELECT sw FROM ServiceWorker sw WHERE sw.department.id = :departmentId")
    List<ServiceWorker> findAllByDepartmentId(@Param("departmentId") Long departmentId);

    List<ServiceWorker> findByDepartmentId(Long departmentId);

    @Query("SELECT sw.workschedules FROM ServiceWorker sw WHERE sw.id = :serviceWorkerId")
    List<Workschedule> findAllWorkschedulesByServiceWorkerId(@Param("serviceWorkerId") Long serviceWorkerId);

    @Query("SELECT sw FROM ServiceWorker sw WHERE sw.user.id = :userId")
    Optional<ServiceWorker> findServiceWorkerByUserId(@Param("userId") Long userId);
}
