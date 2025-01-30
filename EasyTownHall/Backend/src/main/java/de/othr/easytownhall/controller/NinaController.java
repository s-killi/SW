package de.othr.easytownhall.controller;

import de.othr.easytownhall.models.dtos.CovidTickerDTO;
import de.othr.easytownhall.models.dtos.CovidTickerInfoDTO;
import de.othr.easytownhall.services.NinaApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping(("/api/v1/nina"))
public class NinaController {
    private final NinaApiService ninaApiService;


    @GetMapping("/covidticker")
    public Mono<List<CovidTickerDTO>> covidTicker() {
        return this.ninaApiService.getCovidTickerData();
    }
    @GetMapping("/covidticker/data")
    public Mono<List<CovidTickerInfoDTO>> covidTickerData() {
        return this.ninaApiService.getInfosForCovidTickerData(ninaApiService.getCovidTickerData());
    }
}
