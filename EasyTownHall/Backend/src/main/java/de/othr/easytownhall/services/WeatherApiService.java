package de.othr.easytownhall.services;

import de.othr.easytownhall.models.dtos.WeatherDataDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

@Service
public class WeatherApiService {
    private final WebClient webClient;
    @Value("${openweather.api.key}")
    private String apiKey;


    public WeatherApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://api.openweathermap.org/data/2.5")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    public Mono<WeatherDataDTO> getWeatherForRegensburg() {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/weather")
                        .queryParam("q", "Regensburg")
                        .queryParam("units", "metric")
                        .queryParam("appid", apiKey)
                        .queryParam("lang", "de")
                        .build())
                .retrieve()
                .bodyToMono(Map.class)
                .map(this::mapToWeatherDataDTO);
    }

    private WeatherDataDTO mapToWeatherDataDTO(Map<String, Object> response) {
        WeatherDataDTO dto = new WeatherDataDTO();

        Map<String, Object> main = (Map<String, Object>) response.get("main");
        Map<String, Object> wind = (Map<String, Object>) response.get("wind");
        Map<String, Object> sys = (Map<String, Object>) response.get("sys");
        List<Map<String, Object>> weatherList = (List<Map<String, Object>>) response.get("weather");
        Map<String, Object> weather = weatherList.get(0);

        dto.setCityName((String) response.get("name"));
        dto.setTemperature(((Number) main.get("temp")).intValue());
        dto.setHumidity(((Number) main.get("humidity")).intValue());
        dto.setWindSpeed(((Number) wind.get("speed")).floatValue());
        dto.setImage(weather.get("main").toString());
        dto.setDescription((String) weather.get("description"));
        dto.setSunrise(((Number) sys.get("sunrise")).longValue());
        dto.setSunset(((Number) sys.get("sunset")).longValue());

        return dto;
    }

}
