package de.othr.easytownhall.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FootBallStatsDTO {
    private String matchDate;
    private String leagueName;
    private String leagueSeason;
    private String teamOneName;
    private String teamOneIcon;
    private String teamTwoName;
    private String teamTwoIcon;
    private int teamOnePoints;
    private int teamTwoPoints;
}
