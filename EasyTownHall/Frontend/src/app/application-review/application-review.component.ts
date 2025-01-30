import { Component, OnInit } from '@angular/core';
import { ApplicationForm } from '../models/ApplicationForm';
import { FilledAppDTO } from '../models/DTOs/FilledAppDTO';
import { ActivatedRoute, Router } from '@angular/router';
import { ApplicationFormService } from '../services/application-form.service';
import { ApplicationInstanceDTO } from '../models/DTOs/ApplicationInstanceDTO';
import { AuthService } from '../services/auth.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-application-review',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './application-review.component.html',
  styleUrl: './application-review.component.scss'
})
export class ApplicationReviewComponent implements OnInit{
  isLoaded: boolean = false;
  applicationForm!: ApplicationInstanceDTO;
  instanceId!: number;
  isAdmin: boolean  = false;
  isServiceworker: boolean = false;
  isOwner: boolean = false;

  constructor(
        private route: ActivatedRoute,
        private formService: ApplicationFormService,
         private router: Router,
         private authService: AuthService
  ){

  }

  ngOnInit(  ): void {
    this.instanceId = +this.route.snapshot.paramMap.get('id')!;

    this.formService.getSubmittedApplication(this.instanceId).subscribe(
      (data)=>{
       this.applicationForm = data;
       console.log(this.applicationForm);
       this.applicationForm.filledFieldDTOS = data.filledFieldDTOS;
       this.isLoaded=true;



       if(this.applicationForm.user.id == this.authService.getId()){
        this.isOwner = true;
       }
      }
    )
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


  checkBoxString(input: string | null | undefined): string[] {
    // Überprüfen, ob die Eingabe gültig ist, andernfalls leere Liste zurückgeben
    if (!input) {
        return [];
    }
    return input.split('#;#').map(item => item.trim());
  }


  accept(id: number){
    this.formService.acceptSubmittedApplication(id).subscribe(
      (data)=>{
        console.log(data);
        alert('Formular wurde erfolgreich akzeptiert!');
        this.router.navigate(["/application/dashboard"])
      },
      (error) =>{
        alert('Fehler beim Akzeptieren des Formulars.');
      }

    );
  }

  deny(id:number){
    this.formService.denySubmittedApplication(id).subscribe(
      (data)=>{
        console.log(data);
        alert('Formular wurde erfolgreich abgelehnt!');
        this.router.navigate(["/application/dashboard"])
      },
      (error) =>{
        alert('Fehler beim Ablehnen des Formulars.');
      }

    );
  }
  delete(id: number){
    const userId = this.authService.getId();
    this.formService.deleteSubmittedApplication(id).subscribe(
      ()=>{
        this.router.navigate([`/profil/${userId}`]);
      }
    )
  }
}
