package de.othr.easytownhall.models.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @NonNull
    @Column(unique = true)
    private String email;
    @NonNull
    private String password;

    private boolean active;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

}
