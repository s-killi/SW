import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject, switchMap, takeUntil } from 'rxjs';
import { WorkscheduleDTO } from '../models/DTOs/Appointments/WorkscheduleDTO';
import { environment } from '../../environments/environment';
import { SlotRequestDTO } from '../models/DTOs/Appointments/SlotRequestDTO';
import { AppointmentDTO } from '../models/DTOs/Appointments/AppointmentDTO';
import { AuthService } from './auth.service';
import { ServiceworkerAppointmentDTO } from '../models/DTOs/Appointments/ServiceworkerAppointmentDTO';

@Injectable({
  providedIn: 'root'
})
export class AppointmentService {
  getAppointmentById(timeslotId: number): Observable<AppointmentDTO> {
    return this.http.get<AppointmentDTO>(`${environment.baseUrl}workschedule/getAppointment/${timeslotId}`);
  }

  allAppointments = new ReplaySubject<AppointmentDTO[]>(1);
  ngUnsubscribe$ = new Subject<void>();

  constructor(private http: HttpClient, private authService: AuthService) {}

  getWorkschedule(depId: number, date: Date): Observable<WorkscheduleDTO>{
    return this.http.post<WorkscheduleDTO>(
      `${environment.baseUrl}workschedule/checkSlots/${depId}`,
      JSON.stringify(date), 
      {
        headers: { 'Content-Type': 'application/json' }, 
      }
    );
  }

  bookTimeslot(slotRequest: SlotRequestDTO): Observable<any> {
    return this.http.post(`${environment.baseUrl}workschedule/bookTimeslot`, slotRequest);
  }

  getAll(): Observable<AppointmentDTO[]>{
    const userId =  this.authService.getId();
    return this.http.get<AppointmentDTO[]>(`${environment.baseUrl}workschedule/getAllAppointments/${userId}`)
  }

  getAppointments(): Observable<AppointmentDTO[]>{
    return this.allAppointments.asObservable();
  }

  getServiceworkerAppointments(): Observable<ServiceworkerAppointmentDTO[]>{
    const userId = this.authService.getId();
    return this.http.get<ServiceworkerAppointmentDTO[]>(`${environment.baseUrl}workschedule/getServiceworkerAppointments/${userId}`);
  }

  cancelAppointment(timeslotId: number): Observable<boolean>{
    return this.http.get<boolean>(`${environment.baseUrl}workschedule/cancelAppointment/${timeslotId}`)
  }

  refreshAppointments<T>(observable: Observable<T>): void {
      observable.pipe(
        takeUntil(this.ngUnsubscribe$),
        switchMap(() => this.getAll())
      ).subscribe(data => {this.allAppointments.next(data);});
    }

    formatTime(time: string): string{
      time = time.toString();
      const parts = time.split(",", 1);
      console.log(parts[0]);
      return parts[0];
    }

  ngOnDestroy(): void {
    this.ngUnsubscribe$.next();
    this.ngUnsubscribe$.complete();
  }

}
