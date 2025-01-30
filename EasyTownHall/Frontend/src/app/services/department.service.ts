import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Department } from '../models/Department';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {

  constructor(private http: HttpClient) {}

  getAllDepartments(): Observable<Department[]>{
    return this.http.get<Department[]>(`${environment.baseUrl}department`);
  }
}
