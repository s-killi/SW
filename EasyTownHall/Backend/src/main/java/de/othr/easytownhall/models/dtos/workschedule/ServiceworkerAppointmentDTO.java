package de.othr.easytownhall.models.dtos.workschedule;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class ServiceworkerAppointmentDTO {
    private String departmentName;
    private String citizenName;
    private LocalTime startTime;
    private LocalDate date;
    private String reason;
}
