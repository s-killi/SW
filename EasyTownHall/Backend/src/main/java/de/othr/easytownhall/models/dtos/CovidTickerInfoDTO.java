package de.othr.easytownhall.models.dtos;

import lombok.Data;

@Data
public class CovidTickerInfoDTO {
    private String id;
    private String dateOfIssue;
    private String title;
    private String bodyText;
    private String teaserText;
    private boolean push;
    private String lastModificationDate;
    private int version;
}
