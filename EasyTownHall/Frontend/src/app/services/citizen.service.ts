import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginCitizenResponse } from '../models/LoginCitizenResponse';
import { environment } from '../../environments/environment';
import { Profil } from '../models/Profil';
import { AuthService } from './auth.service';
import { CitizenProfilDTO } from '../models/DTOs/CitizenProfilDTO';


@Injectable({
  providedIn: 'root'
})
export class CitizenService {
  
  

  // http client wird injektet
  constructor(private http: HttpClient, private authService: AuthService) { }

  loginRequest(email: string, password: string): Observable<LoginCitizenResponse>{
    const credentials = {email, password};
    return this.http.post<LoginCitizenResponse>(`${environment.baseUrl}auth/login`, credentials);
  }

  registerRequest( 
    email: string, 
    password:string, 
  ): Observable<LoginCitizenResponse>{
      const signUpData = {  email, password };
      return this.http.post<LoginCitizenResponse>(`${environment.baseUrl}auth/register`,signUpData);
    }

    
  getUserProfil(id: number): Observable<Profil>{
    const url_base = `${environment.baseUrl}profil/${id}`;
    return this.http.get<Profil>(url_base);
  }
  getCitizenProfil(id: number): Observable<CitizenProfilDTO>{
    return this.http.get<CitizenProfilDTO>(`${environment.baseUrl}profil/citizen/${id}`);
  }

  updateUserProfil(id: number, profil: Profil): Observable<any> {
    return this.http.put(`${environment.baseUrl}profil/${id}`, profil);
  }
  updateCitizen(id: number, citizenProfil: CitizenProfilDTO): Observable<CitizenProfilDTO> {
    return this.http.put<CitizenProfilDTO>(`${environment.baseUrl}profil/citizen/${id}`, citizenProfil);
  }

  deleteAccount(id:number): Observable<any>{
    return this.http.delete<any>(`${environment.baseUrl}profil/${id}`);
  }

  

}
