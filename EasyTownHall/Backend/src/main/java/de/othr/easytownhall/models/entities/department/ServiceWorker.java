package de.othr.easytownhall.models.entities.department;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.application.ApplicationForm;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ServiceWorker")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class ServiceWorker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonIgnore // Verhindert die Serialisierung
    @NonNull
    private Department department;

    @NonNull
    private String firstname;

    @NonNull
    private String lastname;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "serviceworker_id")
    @JsonManagedReference
    private List<Workschedule> workschedules = new ArrayList<>();

}
