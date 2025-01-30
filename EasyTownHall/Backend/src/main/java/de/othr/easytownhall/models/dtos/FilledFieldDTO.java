package de.othr.easytownhall.models.dtos;

import de.othr.easytownhall.models.enums.FieldType;
import lombok.Data;

@Data
public class FilledFieldDTO {
    private String fieldName;
    private FieldType fieldType;
    private boolean required;
    private String data;
}
