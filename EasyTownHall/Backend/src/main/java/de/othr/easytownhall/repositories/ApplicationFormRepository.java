package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.dtos.ApplicationInstanceShowDTO;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.application.ApplicationForm;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationFormRepository extends JpaRepository<ApplicationForm, Long> {

    @Query("""
    SELECT a
    FROM ApplicationForm a
    WHERE a.version = (
        SELECT MAX(af.version)
        FROM ApplicationForm af
        WHERE af.applicationNumber = a.applicationNumber AND af.isActive = true)
    """)
    List<ApplicationForm> findAllApplicationsUniqueAndActive();

    @Query("""
        SELECT a
        FROM ApplicationForm a
        WHERE a.version = (
            SELECT MAX(af.version)
            FROM ApplicationForm af
            WHERE af.applicationNumber = a.applicationNumber AND af.isActive = true)
        AND LOWER(a.title) LIKE LOWER(CONCAT('%', :title, '%'))
        """)
    List<ApplicationForm> findAllApplicationsUniqueAndActiveWithTitle(@Param("title") String title, Sort sort);


    List<ApplicationForm> findAllByApplicationNumber(Long appNumber);


    Optional<ApplicationForm> findTopByIdOrderByVersionDesc(long id);

    Optional<ApplicationForm> findByIdAndVersion(long id, int version);

    @Query("SELECT MAX(a.version) FROM ApplicationForm a WHERE a.applicationNumber = :applicationNumber")
    Integer getMaxVersion(Long applicationNumber);

    @Query("SELECT MAX(a.applicationNumber) FROM ApplicationForm a")
    Long getMaxApplicationNumber();
}
