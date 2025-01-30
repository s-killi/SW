package de.othr.easytownhall.models.entities.application;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class FilledFormData {
    private Map<String, Object> fieldValues = new HashMap<String, Object>(); // Key = fieldname, Value = data
}
