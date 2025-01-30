package de.othr.easytownhall.mapper;

import de.othr.easytownhall.models.dtos.workschedule.TimeSlotDto;
import de.othr.easytownhall.models.dtos.workschedule.WorkscheduleDTO;
import de.othr.easytownhall.models.entities.Workschedule;
import de.othr.easytownhall.models.entities.appointment.TimeSlot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WorkscheduleMapper {
    public WorkscheduleDTO toDto(Workschedule workschedule, Long swId) {
        WorkscheduleDTO scheduleDto = new WorkscheduleDTO();
        scheduleDto.setId(workschedule.getId());
        scheduleDto.setDate(workschedule.getDate());
        scheduleDto.setTimeSlots(setTimeSlotDto(workschedule.getTimeSlots()));
        scheduleDto.setDepartmentId(workschedule.getDepartment().getId());
        scheduleDto.setServiceworkerId(swId);
        return scheduleDto;
    }

    private List<TimeSlotDto> setTimeSlotDto(List<TimeSlot> timeslots){
        List<TimeSlotDto> timeSlotDtos = new ArrayList<TimeSlotDto>();
        for (TimeSlot timeSlot : timeslots) {
            TimeSlotDto timeSlotDto = new TimeSlotDto();
            timeSlotDto.setTimeslotId(timeSlot.getId());
            timeSlotDto.setStartTime(timeSlot.getStartTime());
            timeSlotDto.setAvailable(timeSlot.isAvailable());
            timeSlotDtos.add(timeSlotDto);
        }
        return timeSlotDtos;
    }
}
