package de.othr.easytownhall.models.entities.application;


import de.othr.easytownhall.models.converters.FilledFormDataConverter;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.enums.ApplicationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "application_instance")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ApplicationInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "application_form_id", referencedColumnName = "id"),
            @JoinColumn(name = "form_version", referencedColumnName = "version")
    })
    @NonNull
    private ApplicationForm applicationForm; // Verweis auf das Formular mit spezifischer Version

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NonNull
    private User user; // Der Bürger, der diesen Antrag ausfüllt

    @NonNull
    private LocalDate submissionDate; // Datum der Einreichung

    @Enumerated(EnumType.STRING)
    @NonNull
    private ApplicationStatus status; // Status des Antrags

    @OneToMany(mappedBy = "applicationInstance", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FilledFormField> filledFields = new ArrayList<>(); // Liste der ausgefüllten Felder

}

