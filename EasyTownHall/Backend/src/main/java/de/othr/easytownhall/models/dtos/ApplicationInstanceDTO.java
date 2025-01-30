package de.othr.easytownhall.models.dtos;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ApplicationInstanceDTO {
    private Long id;
    private LocalDate submissionDate;
    private String status;
    private String applicationFormName;
    private List<String> filledFormData; // Liste der Formulardaten
}

