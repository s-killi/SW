package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.application.FilledFormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FilledFormFieldRepository extends JpaRepository<FilledFormField, Long> {

    @Modifying
    @Query("DELETE  from  FilledFormField f where f.applicationInstance.id = :id")
    void deleteAllByApplicationId(@Param("id")Long id);
}
