package de.othr.easytownhall.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ApplicationSubmitDTO {
    private Map<String, Object> data;
    private String token;
    private int applicationId;
}
