package de.othr.easytownhall.models.dtos;

import de.othr.easytownhall.models.entities.User;
import de.othr.easytownhall.models.enums.ApplicationStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApplicationFilledDTO {
    private Long id;
    private Long ApplicationFormId;
    private UserDto user;
    private LocalDate submissionDate;
    private ApplicationStatus status;
    private String applicationFormName;
    private List<FilledFieldDTO> filledFieldDTOS;

}
