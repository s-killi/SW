package de.othr.easytownhall.models.dtos.workschedule;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDTO {
    private String departmentName;
    private LocalDate date;
    private String reason;
    private Long timeslotId;
    private LocalTime startTime;
}
