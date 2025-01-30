import { Component } from '@angular/core';
import { ApplicationForm } from '../models/ApplicationForm';
import { ApplicationFormService } from '../services/application-form.service';
import { Router } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-citizen-form-list',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './citizen-form-list.component.html',
  styleUrl: './citizen-form-list.component.scss'
})
export class CitizenFormListComponent {
  forms: ApplicationForm[] = [];

  constructor(private formService: ApplicationFormService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.formService.getAllApplicationForms().subscribe((data) => {
      this.forms = data;
    });
  }

  navigateToEdit(id: number){
    console.log(`${id}`);
    this.router.navigate([`application/citizen/fill/${id}`]);
  }
}
