package de.othr.easytownhall.models.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApplicationInstanceShowDTO {
    private Long id;
    private LocalDate submissionDate;
    private String status;
    private String applicationFormName;
}
