import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ApplicationFormValidationService {

  constructor() { }


  validateApplicationFormAdd(data: any): boolean{
    const values = data.value;
    if(!values) return false;
    if(values.description.trim() == "" || values.title.trim() == "") return false;   
    if(values.formFields.length == 0) return false;
    for(let field of values.formFields){
      if(field.fieldName.trim() == "") return false;
    }
    return true;
  }
}
