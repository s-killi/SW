package de.othr.easytownhall.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CovidTickerDTO {
    private String id;
    private String lastModificationDate;
}
