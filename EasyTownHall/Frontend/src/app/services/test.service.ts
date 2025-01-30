import { HttpClient, HttpHeaders } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { TestModel } from '../models/testModel';
import { AuthService } from './auth.service';

//Diese Datei wurde mit diesem command erstellt: ng generate service services/test

@Injectable({
  providedIn: 'root'
})
export class TestService {

  //http = inject(HttpClient);
  constructor(private http: HttpClient, private authService: AuthService) { }
  
  //this.testModel$ = this.http.get<TestModel>(environment.baseUrl + 'test/getFirst');
  
  getHello (): Observable<string>{
    return this.http.get( environment.baseUrl + 'test/hello', {responseType: 'text'});
  }

  getFirstUser(): Observable<TestModel>{
    return this.http.get<TestModel>(environment.baseUrl + 'test/getFirst');
  }

  getUserById(id: number): Observable<TestModel>{
    return this.http.get<TestModel>(environment.baseUrl + `test/getUserById?id=${id}`); //für string interpolation diese ticks `${variable}`
  }
  getAll(): Observable<TestModel[]> {
    return this.http.get<TestModel[]>(environment.baseUrl + 'test/getAll');
  }
  addUser(user: Partial<TestModel>): Observable<TestModel> {
    //
    return this.http.post<TestModel>(environment.baseUrl + 'test/addUser', user);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(environment.baseUrl + `test/deleteUser/${id}`);
  }

  updateUser(id:number , user: Partial<TestModel>): Observable<TestModel>{
    return this.http.put<TestModel>(environment.baseUrl + `test/updateUser/${id}`, user);
  }

}
