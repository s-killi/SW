package de.othr.easytownhall.services;

import de.othr.easytownhall.models.dtos.CovidTickerDTO;
import de.othr.easytownhall.models.dtos.CovidTickerInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NinaApiService {

    private final WebClient webClient;

    public NinaApiService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://warnung.bund.de/api31")
                .defaultHeader(HttpHeaders.ACCEPT, "application/json")
                .build();
    }

    public Mono<List<CovidTickerDTO>> getCovidTickerData() {
        return webClient.get()
                .uri("/appdata/covid/covidticker/DE/covidticker.json")
                .retrieve()
                .bodyToFlux(CovidTickerDTO.class)
                .collectList();
    }

    public Mono<List<CovidTickerInfoDTO>> getInfosForCovidTickerData(Mono<List<CovidTickerDTO>> data) {
        return data.flatMapMany(Flux::fromIterable) // Liste in Flux umwandeln
                .flatMap(dto -> {
                    String id = dto.getId();
                    // Abruf der Daten für jedes DTO
                    return webClient.get()
                            .uri("/appdata/covid/covidticker/DE/tickermeldungen/" + id + ".json")
                            .retrieve()
                            .bodyToMono(CovidTickerInfoDTO.class);
                })
                .collectList(); // Sammle alle Ergebnisse in einer Liste
    }

}
