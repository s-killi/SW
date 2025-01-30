package de.othr.easytownhall.repositories;

import de.othr.easytownhall.models.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByEmail(String email);

    @Query("SELECT DISTINCT p.name FROM User u " +
            "JOIN u.roles r " +
            "JOIN r.privileges p " +
            "WHERE u.email = :email")
    List<String> findPrivilegesByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT p.name FROM User u " +
            "JOIN u.roles r " +
            "JOIN r.privileges p " +
            "WHERE u.id = :id")
    List<String> findPrivilegesById(@Param("id") Long id);



    @Query("SELECT DISTINCT r.name FROM User u " +
            "JOIN u.roles r " +
            "WHERE u.email = :email")
    List<String> findRolesByEmail(@Param("email") String email);



}
