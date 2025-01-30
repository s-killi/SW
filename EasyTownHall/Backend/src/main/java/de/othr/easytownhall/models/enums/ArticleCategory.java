package de.othr.easytownhall.models.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ArticleCategory {
    GENERAL_ANNOUNCEMENTS("Allgemeine Ankündigungen"),
    EVENTS("Veranstaltungen"),
    CONSTRUCTION_AND_TRAFFIC("Bau- und Verkehrsprojekte"),
    CITIZEN_PARTICIPATION("Bürgerbeteiligung"),
    PUBLIC_SAFETY("Öffentliche Sicherheit"),
    EDUCATION_AND_CULTURE("Bildung und Kultur"),
    ENVIRONMENT_AND_SUSTAINABILITY("Umwelt und Nachhaltigkeit"),
    SOCIAL_AND_HEALTH("Soziales und Gesundheit"),
    ECONOMY_AND_BUSINESS("Wirtschaft und Gewerbe"),
    LEISURE_AND_SPORTS("Freizeit und Sport");

    private final String displayName;

    ArticleCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonValue
    public String toJson() {
        return this.displayName;
    }

    @JsonCreator
    public static ArticleCategory fromDescription(String displayName) {
        for (ArticleCategory category : values()) {
            if (category.getDisplayName().equalsIgnoreCase(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Unknown gender: " + displayName);
    }
}
