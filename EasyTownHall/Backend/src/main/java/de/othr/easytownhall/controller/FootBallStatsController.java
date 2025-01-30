package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.dtos.FootBallStatsDTO;
import de.othr.easytownhall.models.dtos.WeatherDataDTO;
import de.othr.easytownhall.services.OpenLigaApiService;
import de.othr.easytownhall.services.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/footballStats")
public class FootBallStatsController {

    @Autowired
    private OpenLigaApiService openLigaApiService;

    @GetMapping
    public Mono<ResponseEntity<List<FootBallStatsDTO>>> getWeather() {
        return this.openLigaApiService.getFootballDataForRegensburg()
                .map(ResponseEntity::ok);
    }
}
