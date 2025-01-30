import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule} from '@angular/router';
import { interval, Observable, switchMap } from 'rxjs';
import {ReactiveFormsModule, FormControl, FormGroup, Validators } from '@angular/forms';
import { TestService } from '../services/test.service';

@Component({
  selector: 'app-test-hello',
  standalone: true,
  imports: [RouterModule, CommonModule, ReactiveFormsModule],
  templateUrl: './test-hello.component.html',
  styleUrls: ['./test-hello.component.scss']
})
export class TestHelloComponent {
  constructor(
    private testService : TestService,
    private router : Router,
  ){}

  
  onPressedButton(): void {
    console.log('Button pressed!');

    this.testService.getHello().subscribe({
      next: (response) => {
        console.log('Response from server:', response);
      },
      error: (error) => {
        console.error('Error occurred:', error);
      },
    });
  }
}
