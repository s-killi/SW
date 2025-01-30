import { Component, OnInit } from '@angular/core';
import { ErrorService } from '../services/error.service';
import { AuthService } from '../services/auth.service';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-error-display',
  standalone: true,
  imports: [TranslateModule],
  templateUrl: './error-display.component.html',
  styleUrl: './error-display.component.scss'
})
export class ErrorDisplayComponent implements OnInit {
  errorMessage: string | null = null;

  constructor(private errorService: ErrorService, public authService: AuthService) {}

  ngOnInit() {
    this.errorService.getErrorMessage().subscribe(message => {
      this.errorMessage = message;
    });
  }

  dismiss() {
    this.errorMessage = null;
  }
}
