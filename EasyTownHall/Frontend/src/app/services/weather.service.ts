import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {WeatherDataDTO} from '../models/DTOs/WeatherDataDTO';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class WeatherService{

  constructor(private http: HttpClient) { }
  
  getWeather (): Observable<WeatherDataDTO>{
    return this.http.get<WeatherDataDTO>(`${environment.baseUrl}weather`)
  }
}
