import { Component, OnInit } from '@angular/core';
import { ApplicationFormService } from '../services/application-form.service';
import { ApplicationForm } from '../models/ApplicationForm';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { TranslateModule } from '@ngx-translate/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ArticlefilterPipe } from '../pipes/articlefilter.pipe';

@Component({
  selector: 'app-admin-form-list',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ArticlefilterPipe, TranslateModule],
  templateUrl: './admin-form-list.component.html',
  styleUrl: './admin-form-list.component.scss'
})
export class AdminFormListComponent implements OnInit{

  forms: ApplicationForm[] = [];
  canEdit: boolean = false;
  isServiceworker: boolean = false;
  isAdmin: boolean = false;

  orderState? : boolean = false; //false= asc, true=desc
  filterControl = new FormControl('');
  searchtext?: String;

  constructor(private formService: ApplicationFormService, private router: Router, public authService: AuthService){
  }

  ngOnInit():void{
    this.formService.getAllApplicationFormsOrdered(this.orderState).subscribe(
      {
        next: (data) => {this.forms = data;}
      }
    )
    this.authService.isAdmin().subscribe(
      (data) => {
        this.isAdmin = data;
        this.canEdit = data;
      }
    )
    this.authService.isServiceWorker().subscribe(
      (data) =>{
        this.canEdit = data;
        this.isServiceworker = data;
      }
    )
  }

  search(): void {
    const searchQuery = this.searchtext?.trim() || '';
    this.formService.getAllApplicationFormsOrdered(this.orderState, searchQuery).subscribe({
      next: (data) => {
        this.forms = data;
      },
      error: (err) => {
        console.error('Error fetching application forms:', err);
      }
    });
  }
  


  changeOrder(): void{
    this.orderState = !this.orderState;
  }


  navigatToDashboard(){
    this.router.navigate([`application/dashboard`]);
  }

  navigateModifyAntrag(application: ApplicationForm) {
    this.router.navigate([`application/service/edit/${application.id}`]);
  }
    
  navigateAntragausfuellen(application: ApplicationForm) {
    this.router.navigate([`application/citizen/fill/${application.id}`]);
  }

}
