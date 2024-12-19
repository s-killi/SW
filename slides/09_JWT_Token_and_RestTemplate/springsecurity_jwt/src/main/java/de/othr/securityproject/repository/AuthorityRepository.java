package de.othr.securityproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import de.othr.securityproject.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
