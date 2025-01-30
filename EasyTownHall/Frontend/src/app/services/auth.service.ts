import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, map, Observable, of, tap } from 'rxjs';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private tokenKey = 'authToken'; // Schlüssel für das Speichern des Tokens in localStorage
  private idKey = 'id';
  private rolesSubject = new BehaviorSubject<{ admin: boolean; serviceWorker: boolean }>({
    admin: false,
    serviceWorker: false,
  });
  //private refreshTokenUrl = environment.baseUrl + '/api/v1/auth/refresh'; // URL zum Erneuern des Tokens

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    const token = this.getToken();
    if(token){
      this.checkRoles(token).subscribe();
    }
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  checkRoles(token: string): Observable<{ admin: boolean; serviceWorker: boolean }> {
    return this.http
      .post<{ admin: boolean; serviceWorker: boolean }>(`${environment.baseUrl}auth/checkRoles`, { token })
      .pipe(
        tap((roles) => {
          this.rolesSubject.next(roles);
          console.log("admin: " + roles.admin + "sw: " + roles.serviceWorker);
        })
      );
  }

  getRoles(): Observable<{ admin: boolean; serviceWorker: boolean }> {
    return this.rolesSubject.asObservable();
  }

  forceReloadRoles(token: string): void {
    this.checkRoles(token).subscribe();
  }

  isAdmin(): Observable<boolean> {
    return this.rolesSubject.asObservable().pipe(map((roles) => roles.admin));
  }

  isServiceWorker(): Observable<boolean> {
    return this.rolesSubject.asObservable().pipe(map((roles) => roles.serviceWorker));
  }

  isCitizen(): Observable<boolean>{
    if(this.isLoggedIn()){
      return this.rolesSubject.asObservable().pipe(
        map(roles => !roles.admin && !roles.serviceWorker)
      );
    }
    return of(false);
  }

  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  clearToken(): void {
    localStorage.removeItem(this.tokenKey);
  }
  
  clearTokenAndRedirect(url: string): Observable<void> {
    this.clearToken();
    window.location.href = url; // Benutzer zur Login-Seite weiterleiten
    return of(void 0); // Ein leeres Observable zurückgeben
  }
  clearLocalStorage(){
    localStorage.clear();
  }

  setId(id: number): void{
    localStorage.setItem(this.idKey, id.toString());
  }
  getId(): number{
    return Number(localStorage.getItem(this.idKey));
  }
  clearId(): void{
    localStorage.removeItem(this.idKey);
  }


  getHeaderWithToken(): HttpHeaders {
    const token = this.getToken();
    const headers: HttpHeaders = new HttpHeaders({
      Authorization: `Bearer ${token}`
    }); 
    return headers;
  }

  isTokenExpiredError(error: HttpErrorResponse): boolean {
    return error.error && error.error.message.includes('Token is invalid or expired');
  }


  /**
   * Abmelden und den Benutzer zur Login-Seite weiterleiten
   */
  logout(): void {
    this.clearToken(); // Token aus dem localStorage entfernen
    this.clearId();
    this.rolesSubject.next({admin: false, serviceWorker: false,});
    window.location.href = '/home'; // Benutzer zur Login-Seite umleiten
  }

  refreshMyToken() {
    const token = this.getToken();

    if (!token) {
      console.error("No token found");
      return;
    }

    this.http.post<{ token: string }>(
      `${environment.baseUrl}auth/refresh-token`,
      { token }
    ).subscribe({
      next: (res) => {
        console.log("Token updated");
        const newToken = res.token; // Der neue Token aus der Antwort
        this.setToken(newToken); // Speichern des neuen Tokens
      },
      error: (err) => {
        console.error("Error refreshing token", err);
        // Optional: Logout oder andere Fehlerbehandlung
      }
    });
  }


}
