package de.othr.easytownhall.models.entities.application;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "filled_form_field")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class FilledFormField {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY) // Correct annotation for a single relationship
    @JoinColumn(name = "form_field_id", nullable = false)
    private FormField formField; // Renamed to camelCase for Java conventions


    @NonNull
    @Lob
    @Column(name = "field_data", nullable = false)
    private String data; // Daten des Felds

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_instance_id", nullable = false)
    private ApplicationInstance applicationInstance; // Verweis auf die zugehörige ApplicationInstance
}
