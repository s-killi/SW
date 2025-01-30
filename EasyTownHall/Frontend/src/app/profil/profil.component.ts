import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CitizenService } from '../services/citizen.service';
import { Profil } from '../models/Profil';
import { FormsModule } from '@angular/forms'; // Importiere FormsModule
import { AuthService } from '../services/auth.service';
import { CitizenProfilDTO } from '../models/DTOs/CitizenProfilDTO';
import { Gender } from '../models/enums/Gender';
import { ApplicationDTO } from '../models/DTOs/ApplicationDTO';
import { ApplicationForm } from '../models/ApplicationForm';
import { ApplicationFormService } from '../services/application-form.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-profil',
  standalone: true,
  imports: [FormsModule, TranslateModule], // FormsModule hier hinzufügen
  templateUrl: './profil.component.html',
  styleUrls: ['./profil.component.scss']
})
export class ProfilComponent implements OnInit {
  id!: number;
  profil: Profil = { id: 0,  email: '', active: true, username: '', password: '',  };
  isEditable: boolean = false;
  citizenProfil!: CitizenProfilDTO;
  genders =  Object.values(Gender);

  pendingApplications!: ApplicationDTO[];
  completedApplications!: ApplicationDTO[];

  constructor(
    private route: ActivatedRoute, 
    private citizenService: CitizenService,
    private authService:AuthService,
    private router: Router,
    private appService: ApplicationFormService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id') || '');
    if(this.id != this.authService.getId()){
      this.router.navigate(["/"]);
    }else{
      if(this.id && this.authService.getId()){
        this.citizenService.getUserProfil(this.id).subscribe({
          next: (profil)  => {
            this.profil = { ... profil};
            this.isEditable =  this.id == this.profil.id;
            if(this.isEditable){
              this.authService.setId(this.profil.id);
            }
          }, 
          error: (error) => {
            console.error('Fehler beim Laden des Profils:', error);
          }
        });
        this.citizenService.getCitizenProfil(this.id).subscribe(
          (response) => {
              console.log(response);
              this.citizenProfil = response;
          }
        );
        this.appService.getPendingdApplications(this.id).subscribe(
          (data) => {
            this.pendingApplications = data;
            console.log(this.pendingApplications);
          }
        )
        this.appService.getComplettedApplications(this.id).subscribe(
          (data) => {
            this.completedApplications = data;
            console.log(this.completedApplications);

          }
        )
        
      }
    }
  }
  goToApplication(subApp: ApplicationDTO) {  
    this.router.navigate([`application/service/review/${subApp.id}`]);
  }


  updateProfile(): void {
    if (!this.isEditable) {
      alert('Sie dürfen dieses Profil nicht bearbeiten.');
      return;
    }

    if(this.citizenProfil){

      this.citizenService.updateCitizen(this.id, this.citizenProfil).subscribe(
        (data) => {
          this.citizenProfil  =data;
        }
      );
    }
    this.citizenService.updateUserProfil(this.id, this.profil).subscribe({
      next: (response) => {
        alert('Profil wurde erfolgreich aktualisiert!');
        this.authService.refreshMyToken();
      },
      error: (error) => {
        alert('Ein Fehler ist aufgetreten!');
      }
    });
  }
  deleteAccount(id: number){
    this.citizenService.deleteAccount(id).subscribe(
      () => {
        console.log("delete" , id);
        this.authService.clearLocalStorage();
        this.router.navigate(['/']);
      }
    )
  }

  genderEquals(first: string, last: string): boolean{
    if(last == null || last == "") return false;
    return first.toLocaleLowerCase() == last.toLocaleLowerCase();
  }
}
