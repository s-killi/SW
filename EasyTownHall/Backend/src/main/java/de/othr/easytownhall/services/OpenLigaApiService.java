package de.othr.easytownhall.services;

import de.othr.easytownhall.models.dtos.FootBallStatsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenLigaApiService {

    private final WebClient webClient;

    public OpenLigaApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openligadb.de")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    public Mono<List<FootBallStatsDTO>> getFootballDataForRegensburg() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/getmatchesbyteamid/181/20/0") // Lädt die letzten 20 Spielwochen des SV Jahn Regensburg
                        .build())
                .retrieve()
                .bodyToMono(List.class)
                .map(this::mapToFootBallStatsDTO);
    }

    private List<FootBallStatsDTO> mapToFootBallStatsDTO(List<Map<String, Object>> response) {
        List<FootBallStatsDTO> dtos = response.stream().map(match -> {
            FootBallStatsDTO dto = new FootBallStatsDTO();

            dto.setMatchDate(((String) match.get("matchDateTime")).substring(0, 10));
            dto.setLeagueName((String) match.get("leagueName"));
            dto.setLeagueSeason(String.valueOf(match.get("leagueSeason")));

            Map<String, Object> team1 = (Map<String, Object>) match.get("team1");
            dto.setTeamOneName((String) team1.get("teamName"));
            dto.setTeamOneIcon((String) team1.get("teamIconUrl"));

            Map<String, Object> team2 = (Map<String, Object>) match.get("team2");
            dto.setTeamTwoName((String) team2.get("teamName"));
            dto.setTeamTwoIcon((String) team2.get("teamIconUrl"));

            List<Map<String, Object>> matchResults = (List<Map<String, Object>>) match.get("matchResults");
            for (Map<String, Object> result : matchResults) {
                if ("Endergebnis".equals(result.get("resultName"))) { //Laden nur das Enderegbenis
                    dto.setTeamOnePoints((int) result.get("pointsTeam1"));
                    dto.setTeamTwoPoints((int) result.get("pointsTeam2"));
                    break;
                }
            }

            return dto;
        }).collect(Collectors.toList());
        Collections.reverse(dtos);
        return dtos;
    }

}
