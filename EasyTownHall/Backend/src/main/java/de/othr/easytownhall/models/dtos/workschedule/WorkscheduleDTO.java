package de.othr.easytownhall.models.dtos.workschedule;

import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WorkscheduleDTO {
    private Long id;

    private Long departmentId;

    private Long serviceworkerId;

    private LocalDate date;

    private List<TimeSlotDto> timeSlots;
}
