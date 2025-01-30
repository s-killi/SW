import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common'; // CommonModule importieren
import { CitizenService } from '../services/citizen.service';
import { LoginCitizenResponse } from '../models/LoginCitizenResponse';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { TranslateModule } from '@ngx-translate/core';
import { ErrorService } from '../services/error.service';


@Component({
  selector: 'app-login-form',
  standalone: true,
  imports: [FormsModule, CommonModule, TranslateModule], // CommonModule hier hinzufügen
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
})
export class LoginFormComponent {
  // TODO: redirect after login/ register
  active: string = 'login';
  firstName: string = '';
  lastName: string = '';
  email: string = '';
  password: string = '';
  gender: string = '';
  birthDate: string = '';

  loginResponse: LoginCitizenResponse | null = null;

  constructor(
    private citizenService: CitizenService, 
    private authService:AuthService, 
    private router: Router,
    private errorService: ErrorService
  ){}

  onLoginTab(): void {
    this.active = 'login';
  }

  onRegisterTab(): void {
    this.active = 'register';
  }

  onSubmitRegister(): void {
    console.log('Register data:', {
      email: this.email,
      password: this.password
    });
    this.citizenService.registerRequest( 
      this.email, this.password).subscribe(
      {
        next:Response=>{
          this.loginResponse = Response;
          console.log('Registration successful', Response);
          this.storeReponse(Response);
          this.authService.forceReloadRoles(Response.token);
          this.router.navigate(['']);
        },
        error:error => {
          console.log('Registration failed', error);
          this.errorService.setErrorMessage(error.error.message);
        }
      }
    )
  }

  onSubmitLogin(): void {
    console.log('Login data:', { email: this.email, password: this.password });
    this.citizenService.loginRequest(this.email, this.password).subscribe(
      {
        next:Response=>{
          this.loginResponse = Response;
          console.log('Login successful', Response);
          this.storeReponse(Response);
          this.authService.forceReloadRoles(Response.token);
          this.router.navigate(['']);
        },
        error:error =>{
        console.log('Login failed', error);
        this.errorService.setErrorMessage(error.error.message);
        }  
      }
    )
    
  }

  storeReponse(Response: LoginCitizenResponse): void{
    const tokenstr = Response.token;
    const id = Response.id;
    if (tokenstr && id) { // Überprüfen, ob das Token nicht null oder leer ist
      this.authService.clearId();
      this.authService.clearToken();
      this.authService.setId(id);
      this.authService.setToken(tokenstr);
      console.log('Token gespeichert:', tokenstr);
    } else {
      console.log('Kein gültiges Token erhalten.');
    }
  }
}
