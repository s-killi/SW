import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserDTO } from '../models/DTOs/UserDTO';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {}

  getAll(): Observable<UserDTO[]>{
    return this.http.get<UserDTO[]>(`${environment.baseUrl}user/getAll`)
  }

  updateRole(id: number, role: string): Observable<UserDTO>{
    console.log(id +" "+ role);
    return this.http.post<UserDTO>(`${environment.baseUrl}user/updateRole/${id}`, role);
  }
}
