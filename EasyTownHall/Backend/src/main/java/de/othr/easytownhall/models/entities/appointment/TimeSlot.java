package de.othr.easytownhall.models.entities.appointment;

import com.fasterxml.jackson.annotation.JsonBackReference;
import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.citizen.Citizen;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TIMESLOT")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class TimeSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private LocalTime startTime;

    @NonNull
    private LocalTime endTime;

    private boolean isAvailable = true;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "citizen_id", referencedColumnName = "id")
    private Citizen citizen;

    //Timeslots für einen Workschedule initialisieren
    public static List<TimeSlot> initializeTimeSlots() {
        List<TimeSlot> timeSlots = new ArrayList<>();
        LocalTime startTime = LocalTime.of(8, 0);

        for (int i = 0; i < 8; i++) {
            LocalTime endTime = startTime.plusHours(1);
            TimeSlot timeSlot = new TimeSlot(startTime, endTime);
            timeSlots.add(timeSlot);
            startTime = endTime;
        }
        return timeSlots;
    }
}
