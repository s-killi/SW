package de.othr.easytownhall.models.entities;


import de.othr.easytownhall.models.enums.PriviligeEnum;
import de.othr.easytownhall.models.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@Entity
@Data
@RequiredArgsConstructor
public class Privilege {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PriviligeEnum name;

    public Privilege(@NonNull final PriviligeEnum name) {
        this.name = name;
    }

}
