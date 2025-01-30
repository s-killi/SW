package de.othr.easytownhall.models.converters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.othr.easytownhall.models.entities.application.FilledFormData;
import jakarta.persistence.AttributeConverter;


public class FilledFormDataConverter implements AttributeConverter<FilledFormData, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(FilledFormData filledFormData) {
        try {
            return mapper.writeValueAsString(filledFormData);
        }catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error serializing FilledFormData", e);
        }
    }

    @Override
    public FilledFormData convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData, FilledFormData.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Error deserializing filled form data", e);
        }
    }

}
