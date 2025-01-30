import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {WeatherDataDTO} from '../models/DTOs/WeatherDataDTO';
import { environment } from '../../environments/environment';
import { FootBallStatsDTO } from '../models/DTOs/FootBallStatsDTO';

@Injectable({
  providedIn: 'root'
})
export class FootballstatsService {

  constructor(private http: HttpClient) { }
  
  getFootballStats (): Observable<FootBallStatsDTO[]>{
    return this.http.get<FootBallStatsDTO[]>(`${environment.baseUrl}footballStats`)
  }
}




