package de.othr.easytownhall.models.dtos.workschedule;

import lombok.Data;

@Data
public class SlotRequestDTO {
    private Long timeslotId;
    private Long userId;
    private String reason;
}

