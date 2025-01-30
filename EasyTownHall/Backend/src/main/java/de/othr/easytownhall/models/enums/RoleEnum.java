package de.othr.easytownhall.models.enums;

public enum RoleEnum {
    CITIZEN("CITIZEN"),
    SERVICEWORKER("SERVICEWORKER"),
    ADMIN("ADMIN");

    private final String description;

    RoleEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
