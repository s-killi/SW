  import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ApplicationForm } from '../models/ApplicationForm';
import { Observable } from 'rxjs';
import { ApplicationInstance } from '../models/ApplicationInstance';
import { ApplicationInstanceDTO } from '../models/DTOs/ApplicationInstanceDTO';
import { ApplicationUpdateDTO } from '../models/DTOs/ApplicationUpdateDTO';
import { ApplicationDTO } from '../models/DTOs/ApplicationDTO';
import { ApplicationStatus } from '../models/enums/ApplicationStatus';

@Injectable({
  providedIn: 'root'
})
export class ApplicationFormService {
  constructor(private http: HttpClient) {}

  /**
   * Ruft alle Antragsformulare ab
   */
  getAllApplicationForms(): Observable<ApplicationForm[]> {
    return this.http.get<ApplicationForm[]>(`${environment.baseUrl}application-forms`);
  }

  getAllApplicationFormsOrdered(ascending: boolean = true, title: string = ''): Observable<ApplicationForm[]> {
    const direction = ascending ? 'asc' : 'desc'; // Richtiges Mapping für die Sortierung
    const titleLike = title == '' ? '' : `&titleLike=${encodeURIComponent(title)}`; 
    const url = `${environment.baseUrl}application-forms?direction=${direction}${titleLike}`;
    return this.http.get<ApplicationForm[]>(url);
  }
  
  /**
   * Ruft ein spezifisches Antragsformular anhand der ID ab
   */
  getApplicationForm(id: number): Observable<ApplicationForm> {
    return this.http.get<ApplicationForm>(`${environment.baseUrl}application-forms/${id}`);
  }

  /**
   * Erstellt ein neues Antragsformular
   */
  createApplicationForm(form: Partial<ApplicationForm>): Observable<ApplicationForm> {

    console.log(form);
    const response = this.http.post<ApplicationForm>(`${environment.baseUrl}application-forms`, form);
    console.log(response);
    return response;
  }


  submitApplication(id: number, filledData: { [key: string]: any }): Observable<ApplicationInstance> {
    return this.http.post<ApplicationInstance>(`${environment.baseUrl}application/${id}/submit`, filledData);
  }


  getSubmittedApplication(id: number): Observable<ApplicationInstanceDTO>{
    return this.http.get<ApplicationInstanceDTO>(`${environment.baseUrl}application/submitted/${id}`);
  }
  updateApplicationForm(form: ApplicationUpdateDTO, id: number): Observable<ApplicationUpdateDTO> {
    return this.http.put<ApplicationUpdateDTO>(`${environment.baseUrl}application-forms/${id}`, form);
  }


  getComplettedApplications(user_id: number): Observable<ApplicationDTO[]>{
    return this.http.get<ApplicationDTO[]>(`${environment.baseUrl}application/completted/${user_id}/submitted`);
  }
  getPendingdApplications(user_id: number): Observable<ApplicationDTO[]>{
    return this.http.get<ApplicationDTO[]>(`${environment.baseUrl}application/completted/${user_id}/pending`);
  }

  
  

  acceptSubmittedApplication(id: number):Observable<any> {
    return this.http.post<any>(`${environment.baseUrl}application/submitted/${id}/accept`, true);
  }
  denySubmittedApplication(id: number):Observable<any> {
    return this.http.post<any>(`${environment.baseUrl}application/submitted/${id}/accept`, false);
  }

  deleteSubmittedApplication(id:number): Observable<any>{
    return this.http.delete<any>(`${environment.baseUrl}application/submitted/${id}`);
  }
}
