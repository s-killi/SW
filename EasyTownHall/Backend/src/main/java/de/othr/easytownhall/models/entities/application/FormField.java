package de.othr.easytownhall.models.entities.application;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import de.othr.easytownhall.models.enums.FieldType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "form_field")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class FormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String fieldName;

    @NonNull
    @Enumerated(EnumType.STRING)
    private FieldType fieldType;

    private boolean isRequired; // Feld obligatorisch?

    private String defaultValue; // Standardwert für das Feld (optional)

    private String labelHelp; // optionales Help-Label

    @ElementCollection
    @CollectionTable(name = "form_field_data", joinColumns = @JoinColumn(name = "form_field_id"))
    @Column(name = "value_column") // Spalte für die Werte in der Liste
    private List<String> data = new ArrayList<>(); // Liste mit String-Werten

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "antragsformular_id")
    @JsonIgnore
    private ApplicationForm antragsformular; // Referenz auf das zugehörige Formular
}
