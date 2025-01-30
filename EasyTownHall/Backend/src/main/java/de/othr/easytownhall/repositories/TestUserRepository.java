package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.TestUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestUserRepository extends JpaRepository<TestUserModel, Long> { // Modelnamen und den primary key angeben

}
