package de.othr.easytownhall.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Male"),
    FEMALE("Female"),
    OTHER("Other");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    @JsonValue
    public String toJson() {
        return this.description;
    }

    @JsonCreator
    public static Gender fromDescription(String description) {
        for (Gender gender : values()) {
            if (gender.getDescription().equalsIgnoreCase(description)) {
                return gender;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + description);
    }
}
