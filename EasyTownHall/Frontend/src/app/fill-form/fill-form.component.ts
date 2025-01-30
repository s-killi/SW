import { Component, OnInit } from '@angular/core';
import { ApplicationForm } from '../models/ApplicationForm';
import { FormArray, FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { ApplicationFormService } from '../services/application-form.service';
import { BrowserModule } from '@angular/platform-browser';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';
import { FilledAppDTO } from '../models/DTOs/FilledAppDTO';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-fill-form',
  standalone: true,
  imports: [FormsModule, ReactiveFormsModule, CommonModule, TranslateModule],
  templateUrl: './fill-form.component.html',
  styleUrl: './fill-form.component.scss'
})
export class FillFormComponent implements OnInit{

  isLoaded: boolean = false;
  applicationForm!: ApplicationForm;
  form!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private formService: ApplicationFormService,
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router,
  ){}


  
  ngOnInit(): void {
    const id = +this.route.snapshot.paramMap.get('id')!;
    this.formService.getApplicationForm(id).subscribe((form) => {
      this.applicationForm = form;
      this.isLoaded = true;
      console.log(this.applicationForm)
      this.createForm();
    });
  }
  
  createForm() {
    const controls: any = {};
  
    this.applicationForm.formFields.forEach((field) => {
      if (field.fieldType === 'CHECKBOX') {
        // Initialisiere Checkboxen als FormArray mit `false`
        controls[field.fieldName] = this.fb.array(
          field.data ? field.data.map(() => false) : [] // Sicherstellen, dass `data` existiert
        );
      } else {
        controls[field.fieldName] = [
          field.defaultValue || '',
          field.isRequired ? Validators.required : null,
        ];
      }
    });
  
    this.form = this.fb.group(controls);
  }
  
  submitForm() {
    if (this.form.valid) {
      const filledData: Record<string, any> = {};
  
      this.applicationForm.formFields.forEach((field) => {
        const fieldId = field.id; // Angenommen, `fieldName` ist die eindeutige ID oder nutze `field.id` falls vorhanden.
  
        if (field.fieldType === 'CHECKBOX') {
          // Hole die ausgewählten Checkbox-Werte
          filledData[fieldId] = this.getCheckedValues(field.fieldName, field.data || []);
        } else {
          // Anderen Wert direkt speichern
          filledData[fieldId] = this.form.get(field.fieldName)?.value;
        }
      });
  
      const token = this.authService.getToken() || '';
      const id = +this.route.snapshot.paramMap.get('id')!;
      const request: FilledAppDTO = {
        data: filledData,
        token: token ,
        application_id: id
      };
      console.log(request);

      this.formService.submitApplication(this.applicationForm.id, request).subscribe(
        (response) => {
          console.log('Formular erfolgreich eingereicht:', response);
          alert('Formular wurde erfolgreich eingereicht!');
          this.router.navigate(['/application']);
        },
        (error) => {
          console.error('Fehler beim Einreichen des Formulars:', error);
          alert('Fehler beim Einreichen des Formulars.');
        }
      );
    } else {
      alert('Bitte alle erforderlichen Felder ausfüllen.');
    }
  }
  
  
  onFileChange($event: Event,arg1: string) {
    throw new Error('Method not implemented.');
  }

  entriesToMap(obj: any): { key: string; value: string }[] {
    if (!obj) {
      console.log("Das Objekt ist leer oder null.");
      return []; // Gib ein leeres Array zurück, wenn das Objekt leer ist
    }
  
    const response = new Array();
    const iteration = Object.entries(obj);
    for(let i = 0; i < iteration.length; i++){
      response.push(iteration[i]);
    }
    console.log(response.length);
    return response;
  }

  getCheckedValues(fieldName: string, fieldData: string[]): string[] | null {
    const formArray = this.form.get(fieldName) as FormArray;
    return formArray.controls
      .map((control, index) => (control.value ? fieldData[index] : null))
      .filter((value) => value !== null); // Nur ausgewählte Werte
  }
  


}


