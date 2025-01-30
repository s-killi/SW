package de.othr.easytownhall.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {
    public String cityName;
    public int temperature;
    public int humidity;
    public float windSpeed;
    public String image;
    public String description;
    private long sunrise;
    private long sunset;
}
