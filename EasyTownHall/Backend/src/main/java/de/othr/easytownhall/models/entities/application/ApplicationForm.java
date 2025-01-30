package de.othr.easytownhall.models.entities.application;

import java.util.ArrayList;
import java.util.List;

import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.models.entities.department.ServiceWorker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name="ApplicationForm", uniqueConstraints = {
        @UniqueConstraint(name="UniqueApplicationNumberAndVersion",columnNames = {"applicationNumber", "version"})
})
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ApplicationForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Long applicationNumber;

    private int version;

    @NonNull
    private String title;
    @NonNull
    private String description;

    @OneToMany(mappedBy = "antragsformular", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FormField> formFields = new ArrayList<>(); // Eingabefelder des Formulars

    private boolean isActive = true;
}
