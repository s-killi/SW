import { CommonModule } from '@angular/common';
import { Component} from '@angular/core';
import { RouterModule} from '@angular/router';
import { HeaderComponent } from "./header/header.component";
import { ErrorDisplayComponent } from './error-display/error-display.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule, HeaderComponent, ErrorDisplayComponent],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  constructor() {
    this.clearLocalStorageOnFirstLoad();
  }

  clearLocalStorageOnFirstLoad() {
    const isFirstLoad = localStorage.getItem('isFirstLoad');

    if (!isFirstLoad) {
      console.log('Clearing LocalStorage on first load...');
      localStorage.clear();
      localStorage.setItem('isFirstLoad', 'true');
    } 
  }
}