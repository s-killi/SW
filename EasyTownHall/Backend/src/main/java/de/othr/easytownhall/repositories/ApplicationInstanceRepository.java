package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.application.ApplicationInstance;
import de.othr.easytownhall.models.enums.ApplicationStatus;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationInstanceRepository extends JpaRepository<ApplicationInstance, Long> {
    @Query("SELECT ai FROM ApplicationInstance ai WHERE ai.status = :status")
    List<ApplicationInstance> findByStatus(@Param("status") ApplicationStatus status);

    @Query("SELECT ai FROM ApplicationInstance ai WHERE ai.status IN (:statuses) AND ai.user = :user")
    List<ApplicationInstance> findAllByStatusInAndUser(@Param("statuses") List<ApplicationStatus> statuses,
                                                       @Param("user") User user);

    @Query("SELECT ai from ApplicationInstance ai where ai.user.id = :user_id")
    List<ApplicationInstance> findByUser(@Param("user_id")Long userId);
    @Modifying
    @Query("DELETE FROM ApplicationInstance a WHERE a.user.id = :id")
    void deleteByUserIdQuery(@Param("id")Long id);
}
