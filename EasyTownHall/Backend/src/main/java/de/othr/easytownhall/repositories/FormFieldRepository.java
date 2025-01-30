package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.application.FormField;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldRepository extends JpaRepository<FormField, Long> {
}
