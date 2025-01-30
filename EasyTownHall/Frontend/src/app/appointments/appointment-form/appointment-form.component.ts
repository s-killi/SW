import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Department } from '../../models/Department';
import { DepartmentService } from '../../services/department.service';
import { Observable } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { WorkscheduleDTO } from '../../models/DTOs/Appointments/WorkscheduleDTO';
import { AppointmentService } from '../../services/appointment.service';
import { AuthService } from '../../services/auth.service';
import { SlotRequestDTO } from '../../models/DTOs/Appointments/SlotRequestDTO';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-appointment-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  templateUrl: './appointment-form.component.html',
  styleUrl: './appointment-form.component.scss'
})
export class AppointmentFormComponent {
  public appointmentForm: FormGroup;
  timeSlotForm: FormGroup;
  timeslotId?: number;
  isEditMode = false;
  departments$? : Observable<Department[]>
  workschedule$?: Observable<WorkscheduleDTO>

  constructor(
    private fb: FormBuilder,
    private departmentService: DepartmentService,
    public appointmentService: AppointmentService,
    private authService: AuthService,
    private router: Router,
    private route: ActivatedRoute,
    private translate: TranslateService
  ) {
    this.appointmentForm = this.fb.group({
      department: [null, Validators.required],
      reason: ['', [Validators.required, Validators.maxLength(200)]],
      date: [null, Validators.required],
    });

    this.timeSlotForm = this.fb.group({
      timeslot: [null, Validators.required]
    })
  }

  ngOnInit(): void {
    this.loadDepartments();
    this.route.params.subscribe(params => {
      this.timeslotId = +params['id'];
      if (this.timeslotId) {
        this.isEditMode = true;
        this.loadAppointment();
      }
    });
  }

  loadAppointment() {
    if(this.timeslotId){
      this.appointmentService.getAppointmentById(this.timeslotId).subscribe(appointment =>{
        this.appointmentForm.patchValue({
          reason: appointment.reason
        })
      })
    }
  }

  loadDepartments(): void {
    this.departments$ = this.departmentService.getAllDepartments();
  }

  submitTimeSlotCheck(): void {
    if (this.appointmentForm.valid) {
      console.log('Form submitted:', this.appointmentForm.value);
      const depId = this.appointmentForm.value.department;
      const date = this.appointmentForm.value.date;
      console.log("id", depId);
      console.log("date", date);
      this.workschedule$ = this.appointmentService.getWorkschedule(depId, date);
    } 
    else {
      console.log('Form is invalid');
    }
  }

  submitForm(): void{
    if(this.appointmentForm.valid && this.timeSlotForm.valid){
      const request: SlotRequestDTO = {
        timeslotId: this.timeSlotForm.value.timeslot,
        userId: this.authService.getId(),   
        reason: this.appointmentForm.value.reason, 
      };
      this.appointmentService.bookTimeslot(request).subscribe({
        next: () =>{
          
          if(this.isEditMode && this.timeslotId){
            this.appointmentService.refreshAppointments(this.appointmentService.cancelAppointment(this.timeslotId)); //letzten termin canceln
            alert("Termin erfolgreich aktualisiert!");
          }
          else{
            alert("Termin erfolgreich beantragt!");
          }
          this.router.navigate([`appointments`]);
        },
        error: () => {
          alert("Ein Fehler ist aufgetreten");
        }
      });
    }
  }

}
