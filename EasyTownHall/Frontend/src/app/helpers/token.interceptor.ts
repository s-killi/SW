import { Injectable } from '@angular/core';
import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler,
  HttpEvent,

  HttpErrorResponse,
} from '@angular/common/http';
import { from, Observable, throwError } from 'rxjs';
import { catchError, switchMap } from 'rxjs/operators';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { ErrorService } from '../services/error.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  private excludedUrls = ['https://api.openweathermap.org'];

  constructor(private authService: AuthService, private router: Router, private errorService: ErrorService) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    // Falls die URL in der Liste der ausgeschlossenen URLs ist, leite die Anfrage direkt weiter.
    if (this.excludedUrls.some(url => request.url.startsWith(url))) {
      return next.handle(request);
    }
  
    // Erstelle die Anfrage ggf. mit Token, falls der Benutzer eingeloggt ist.
    const clonedRequest = this.authService.isLoggedIn() && this.authService.getToken()
      ? request.clone({
          setHeaders: {
            Authorization: `Bearer ${this.authService.getToken()}`,
          },
        })
      : request;
  
    // Bearbeite die Anfrage und fange mögliche Fehler ab.
    return next.handle(clonedRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        if (error.status === 401) {
          this.errorService.setErrorMessage(
            'Unauthorized: Session expired or missing privileges.'
          );
        }
        return throwError(error);
      })
    );
  }
  
}
