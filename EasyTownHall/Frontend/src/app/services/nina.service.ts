import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { CovidTickerDTO } from '../models/DTOs/CovidTickerDTO';
import { CovidTickerInfoDTO } from '../models/DTOs/CovidTickerInfoDTO';

@Injectable({
  providedIn: 'root'
})
export class NinaService {

  constructor(private http: HttpClient) { }

  getCovidData(): Observable<CovidTickerDTO[]>{
    return this.http.get<CovidTickerDTO[]>(`${environment.baseUrl}nina/covidticker`);
  }
  getCovidDataFullInfo(): Observable<CovidTickerInfoDTO[]>{
    return this.http.get<CovidTickerInfoDTO[]>(`${environment.baseUrl}nina/covidticker/data`);
  }
}
