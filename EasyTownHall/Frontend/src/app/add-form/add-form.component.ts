import { Component, OnInit } from '@angular/core';
import { ApplicationForm } from '../models/ApplicationForm';
import { FormField } from '../models/FormField';
import { FieldType } from '../models/enums/FieldType';
import { FormFieldDTO } from '../models/DTOs/FormFieldDTO';
import { FormArray, FormBuilder, FormGroup, FormsModule, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ApplicationFormService } from '../services/application-form.service';
import { ApplicationFormValidationService } from '../services/application-form-validation.service';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationUpdateDTO } from '../models/DTOs/ApplicationUpdateDTO';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-add-form',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './add-form.component.html',
  styleUrls: ['./add-form.component.scss']
})
export class AddFormComponent implements OnInit{  
  form: FormGroup;
  fieldTypes = Object.values(FieldType); // Enum to array
  dataSize: number = 1;
  id: number = 0;
  isUpdate:boolean = false;
  isAdmin: boolean = false;
  isServiceworker: boolean = false;

  constructor(
    private fb: FormBuilder, 
    private applicationFormService: ApplicationFormService, 
    private appvalidService: ApplicationFormValidationService, 
    private router: Router,
    private route: ActivatedRoute,
    public authService: AuthService
  ) 
    {
    this.form = this.fb.group({
      title: ['', Validators.required],
      description: [''],
      formFields: this.fb.array([])
    });
  }

  ngOnInit(): void {
    this.id =  +this.route.snapshot.paramMap.get('id')!;
    console.log(this.id);
    if(this.id > 0){
      this.getDataFromBackend(this.id);
      this.isUpdate = true;
    }
    this.authService.isAdmin().subscribe(
      (data) => {
        this.isAdmin = data;
      }
    )
    this.authService.isServiceWorker().subscribe(
      (data) =>{
        this.isServiceworker = data;
      }
    )
  }

  get formFields(): FormArray {
    return this.form.get('formFields') as FormArray;
  }

  getDataFromBackend(id: number){
    this.applicationFormService.getApplicationForm(id).subscribe(
      (data) => {
        this.form.patchValue({title: data.title});
        this.form.patchValue({description: data.description});

        for(let i = 0; i < data.formFields.length; i++){
          const insertData: string[] = [];
          if (data.formFields[i].data && data.formFields[i].data.length > 0) {
            console.log('Data exists and is not empty:', data.formFields[i].data);
            for(let j = 0; j < data.formFields[i].data.length; j++){
              insertData.push(data.formFields[i].data[j]);
            }
          }
          
          console.log(data.formFields[i]);
          const field = this.fb.group({
            fieldName: [data.formFields[i].fieldName, Validators.required
            ],
            fieldType: [data.formFields[i].fieldType],
            isRequired: [data.formFields[i].isRequired],
            defaultValue: [data.formFields[i].defaultValue],
            labelHelp: [data.formFields[i].labelHelp],
            data: this.fb.array(insertData)
          });
          this.formFields.push(field);
        }
        

        
      }
    )
  }

  addField(): void {
    const field = this.fb.group({
      fieldName: ['', Validators.required
      ],
      fieldType: [FieldType.TEXT],
      isRequired: [false],
      defaultValue: [''],
      labelHelp: [''],
      data: this.fb.array(['',''])
    });
    this.formFields.push(field);
  }
  
  addData(dataIn: string, index: number): void {
    const field = this.formFields.at(index); // Zugriff auf das spezifische Feld
    const dataArray = field.get('data') as FormArray;

    dataArray.push(this.fb.control(dataIn));
  }
  


  removeField(index: number): void {
    this.formFields.removeAt(index);
  }
  

  updateSelectData(event: Event, index: number): void {
    const inputElement = event.target as HTMLInputElement;
    if (!inputElement.value) {
      return;
    }

    const dataArray = this.fb.array([]);
    inputElement.value.split(',').forEach(value => {
      if (value.trim()) {
        dataArray.push(this.fb.control(value.trim()));
      }
    });

    this.formFields.at(index).patchValue({ data: dataArray });
  }

  publishForm(): void {
    if (this.appvalidService.validateApplicationFormAdd(this.form)) {
      let appId: number = 0;
      if(this.isUpdate){
        appId = this.id;
      }
      const applicationForm: ApplicationUpdateDTO = {
        title: this.form.value.title,
        description: this.form.value.description,
        version: 1,
        formFields: this.formFields.value.map((field: any, index: number) => ({
          id: index + 1,
          fieldName: field.fieldName,
          fieldType: field.fieldType,
          isRequired: field.isRequired,
          defaultValue: field.defaultValue,
          labelHelp: field.labelHelp,
          data: field.data
        }))
      };
      if(this.isUpdate){
        this.applicationFormService.updateApplicationForm(applicationForm, this.id).subscribe(
          (data) => {
            console.log(data);
            alert('Formular erfolgreich geupdatet!');
            this.router.navigate(["application/dashboard"]);
          }
        );
      }else{
      this.applicationFormService.createApplicationForm(applicationForm).subscribe(
        (data) => {
          console.log(data);
          alert('Formular erfolgreich veröffentlicht!');
          this.router.navigate(["application/dashboard"]);
        }
      );
    }
      
    } else {
      alert('Bitte alle erforderlichen Felder ausfüllen!');
    }
  }

  private convertToMap(data: any): Map<string, string> {
    if (typeof data === 'string') {
      const map = new Map<string, string>();
      data.split(',').forEach(pair => {
        const [key, value] = pair.split(':');
        if (key && value) {
          map.set(key.trim(), value.trim());
        }
      });
      return map;
    }
    return new Map();
  }

  onFieldTypeChange(event: Event, index: number): void {
    const selectElement = event.target as HTMLSelectElement; // Cast zum HTMLSelectElement
    const selectedValue: number = Number(selectElement.value); // Zugriff auf den ausgewählten Wert
  
    // Aktualisiere das entsprechende FormField
    this.formFields.at(index).patchValue({ fieldType: this.fieldTypes.at(selectedValue) });
  }
  getArrayFromNumberFieldData(field: any): number[] {
    const data = field.value.data;
  
    if (data && data instanceof Map) {
      return Array.from({ length: data.size }, (_, i) => i);
    } else if (data && typeof data === 'object') {
      return Array.from({ length: Object.keys(data).length }, (_, i) => i);
    }


    return [0]; // Dummy-Wert, falls keine Daten vorhanden sind
  }
  addDropdownOption(index: number): void {
    const field = this.formFields.at(index);
    const dataArray = field.get('data') as FormArray;
    dataArray.push(this.fb.control(''));
  }
  

  deleteOption(fieldIndex: number, optionIndex: number): void {
    const field = this.formFields.at(fieldIndex);
    const dataArray = field.get('data') as FormArray;

    if (dataArray.length > 2) {
      dataArray.removeAt(optionIndex);
    } else {
      alert('Es müssen mindestens zwei Optionen vorhanden sein.');
    }
  }
}
function next(value: ApplicationForm): void {
  throw new Error('Function not implemented.');
}

