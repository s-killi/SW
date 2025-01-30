import { Component } from '@angular/core';
import { WeatherService } from '../services/weather.service';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { WeatherDataDTO } from '../models/DTOs/WeatherDataDTO';

@Component({
  selector: 'app-weather',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './weather.component.html',
  styleUrl: './weather.component.scss'
})
export class WeatherComponent {
  city: string = 'Regensburg';
  weatherData$: Observable<WeatherDataDTO> | undefined;
  constructor(private weatherService: WeatherService){}

  ngOnInit(): void {
    this.getWeather();
  }

  getWeather(){
    this.weatherData$ = this.weatherService.getWeather();
  }

  formatTime(timestamp: number): string {
    const date = new Date(timestamp * 1000); 
    const hours = date.getHours().toString().padStart(2, '0'); // Stunden im Format 'hh'
    const minutes = date.getMinutes().toString().padStart(2, '0'); // Minuten im Format 'mm'
    return `${hours}:${minutes}`;
  }
}
