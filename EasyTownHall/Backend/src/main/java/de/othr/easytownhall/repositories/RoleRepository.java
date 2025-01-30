package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.Role;
import de.othr.easytownhall.models.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleEnum name);
}