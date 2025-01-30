package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Long>{
    @Query("SELECT c FROM Citizen c WHERE c.user.id = :userId")
    Optional<Citizen> findByUserId(@Param("userId") Long userId);
    Optional<Citizen> findByUser(User user);
    @Modifying
    @Query("DELETE FROM Citizen c WHERE c.user.id = :userId")
    void deleteByUserIdQuery(@Param("userId") Long userId);
}
