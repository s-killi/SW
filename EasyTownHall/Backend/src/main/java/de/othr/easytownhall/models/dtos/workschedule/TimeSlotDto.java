package de.othr.easytownhall.models.dtos.workschedule;

import lombok.Data;

import java.time.LocalTime;

@Data
public class TimeSlotDto {
    private Long timeslotId;
    private LocalTime startTime;
    private boolean isAvailable;
}
