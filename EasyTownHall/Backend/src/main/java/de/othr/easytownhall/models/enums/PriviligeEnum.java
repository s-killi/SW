package de.othr.easytownhall.models.enums;

public enum PriviligeEnum {
    READ_PRIVILEGE("READ_PRIVILIGE"),
    WRITE_PRIVILEGE("WRITE_PRIVILIGE");

    private final String description;

    PriviligeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
