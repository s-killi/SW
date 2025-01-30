package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.dtos.WeatherDataDTO;
import de.othr.easytownhall.services.WeatherApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherApiService weatherApiService;

    @GetMapping
    public Mono<ResponseEntity<WeatherDataDTO>> getWeather() {
        return this.weatherApiService.getWeatherForRegensburg()
                .map(ResponseEntity::ok);
    }
}
