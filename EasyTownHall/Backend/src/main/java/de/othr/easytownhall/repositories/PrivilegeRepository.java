package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.Privilege;
import de.othr.easytownhall.models.enums.PriviligeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByName(PriviligeEnum name);

}
