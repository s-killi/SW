package de.othr.im.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import de.othr.im.model.WorkshopSubscription;


public interface WorkshopSubscriptionRepository extends PagingAndSortingRepository<WorkshopSubscription, Long> {

	
	 //List<WorkshopSubscription> findWorkshopSubscriptionsByStudentId(Long studentId);
	@Query("select ws from WorkshopSubscription ws where ws.student.id=:idstudent")
	List<WorkshopSubscription> findWorkshopSubscriptionsbyStudent(@Param("idstudent") Long idstudent);
	
	@Query("select ws FROM WorkshopSubscription ws where ws.student.id=idstudent")
	List <WorkshopSubscription> findAllWithPagination(@Param("idstudent") Long idstudent, Pageable pageable);
	
	@Query("select ws FROM WorkshopSubscription ws where ws.student.id=idstudent")
	Page<WorkshopRepository> findPageWithPagination(@Param("idstudent") Long idstudent, Pageable pageable);
}

