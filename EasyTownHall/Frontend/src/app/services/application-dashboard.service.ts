import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { AuthService } from './auth.service';
import { Observable } from 'rxjs';
import { ApplicationDTO } from '../models/DTOs/ApplicationDTO';
import { environment } from '../../environments/environment';
import { ApplicationForm } from '../models/ApplicationForm';

@Injectable({
  providedIn: 'root'
})
export class ApplicationDashboardService {

  constructor(private http: HttpClient, private authService: AuthService) { }
  

  getSubmittedApplications(): Observable<ApplicationDTO[]>{
    return this.http.get<ApplicationDTO[]>(`${environment.baseUrl}application/submitted`);
  }

  archiveApplication(id: number, form: ApplicationForm): Observable<ApplicationForm>{
    return this.http.put<ApplicationForm>(`${environment.baseUrl}application-forms/archive/${id}`, form);
  }
}
