import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ErrorService {

  constructor() { }

  private errorSubject = new Subject<string>();

  getErrorMessage() {
    return this.errorSubject.asObservable();
  }

  setErrorMessage(message: string) {
    this.errorSubject.next(message);
  }
}
