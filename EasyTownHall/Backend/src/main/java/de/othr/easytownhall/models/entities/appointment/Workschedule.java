package de.othr.easytownhall.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import de.othr.easytownhall.models.entities.department.Department;
import de.othr.easytownhall.models.entities.department.ServiceWorker;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "WORKSCHEDULE")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class Workschedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalDate date;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workschedule_id")
    private List<TimeSlot> timeSlots = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    @JsonBackReference
    @NonNull
    private Department department;
}
