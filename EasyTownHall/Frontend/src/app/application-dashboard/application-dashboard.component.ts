import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ApplicationDashboardService } from '../services/application-dashboard.service';
import { ApplicationDTO } from '../models/DTOs/ApplicationDTO';
import { ApplicationForm } from '../models/ApplicationForm';
import { ApplicationFormService } from '../services/application-form.service';
import { AuthService } from '../services/auth.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-application-dashboard',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './application-dashboard.component.html',
  styleUrl: './application-dashboard.component.scss'
})
export class ApplicationDashboardComponent implements OnInit{

  submittedApplications!: ApplicationDTO[];
  forms: ApplicationForm[] = [];
  isServicworker: boolean = false;
  isAdmin: boolean = false;
  
  constructor(
    private router: Router,
    private appDashboard: ApplicationDashboardService,
    private formService: ApplicationFormService, public authService: AuthService
  ){

  }

  ngOnInit(): void {
    this.appDashboard.getSubmittedApplications().subscribe(
      (data)=>{
        this.submittedApplications = data;
      }
    )
    this.formService.getAllApplicationForms().subscribe(
      {
        next: (data) => {this.forms = data;}
      }
    )
    this.authService.isAdmin().subscribe(
      (data) => {
        this.isAdmin = data;
      }
    )
    this.authService.isServiceWorker().subscribe(
      (data) =>{
        this.isServicworker = data;
      }
    )

  }
  //
  archiveForm(id: number, form: ApplicationForm){
    console.log("archive", id);
    this.appDashboard.archiveApplication(id, form).subscribe(
      ()=>{
        console.log("archived file");
        window.location.reload();
      }
    );
  }


  // 
  goToApplication(subApp: ApplicationDTO) {  
    this.router.navigate([`application/service/review/${subApp.id}`]);
  }

  // Nav
  navToNewApplication() {
    this.router.navigate([`application/service/add`]);
  }

  navigateModifyAntrag(application: ApplicationForm) {
    this.router.navigate([`application/service/edit/${application.id}`]);
  }
  
}
