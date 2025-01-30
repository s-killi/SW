import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { AppointmentDTO } from '../../models/DTOs/Appointments/AppointmentDTO';
import { AppointmentService } from '../../services/appointment.service';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule, DatePipe } from '@angular/common';
import { ArticlefilterPipe } from '../../pipes/articlefilter.pipe';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { ServiceworkerAppointmentDTO } from '../../models/DTOs/Appointments/ServiceworkerAppointmentDTO';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-appointments',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ArticlefilterPipe, DatePipe, TranslateModule],
  templateUrl: './appointments.component.html',
  styleUrl: './appointments.component.scss'
})
export class AppointmentsComponent {

    appointments$?: Observable<AppointmentDTO[]>;
    serviceWorkerAppointments$?: Observable<ServiceworkerAppointmentDTO[]>
    filterControl = new FormControl('');
    searchtext? : any;

    constructor(public appointmentService: AppointmentService, private router: Router, public authService: AuthService, private translate: TranslateService){
      this.appointmentService.refreshAppointments(this.appointmentService.getAll());
      this.serviceWorkerAppointments$ = this.appointmentService.getServiceworkerAppointments();
      this.appointments$ = this.appointmentService.getAppointments();
    }

    updateAppointment(timeslotId: number) {
      this.router.navigate(['appointments/update', timeslotId]) ;
    }

    cancelAppointment(timeslotId: number) {
      this.appointmentService.refreshAppointments(this.appointmentService.cancelAppointment(timeslotId));
    }
}
