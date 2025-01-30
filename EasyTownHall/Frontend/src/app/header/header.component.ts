import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss'
})
export class HeaderComponent {
  roles?: { admin: boolean; serviceWorker: boolean };

  constructor(public authService: AuthService, private router: Router, private translate: TranslateService){}

  ngOnInit(): void {
    if(this.authService.isLoggedIn()){
      const token = this.authService.getToken();
    this.authService.forceReloadRoles(token??"");
    this.authService.getRoles().subscribe((roles) => {
      this.roles = roles;
    });
    if(this.roles){
      console.log("admin: " + this.roles.admin + "sw: " + this.roles.serviceWorker);
    }
    // Sprache beim Start wiederherstellen
    const savedLang = localStorage.getItem('lang') || 'de'; // Fallback auf Deutsch
    this.translate.use(savedLang);
    }
  }

  toProfil(){
    const id = this.authService.getId();
    const url = `profil/${id}`
    this.router.navigate([url]);
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['']);
  }
  switchLang(lang: string) {
    this.translate.use(lang);
    localStorage.setItem('lang', lang);
  }
  
}
