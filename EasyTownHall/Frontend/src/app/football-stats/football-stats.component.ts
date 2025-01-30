import { Component } from '@angular/core';
import { FootballstatsService } from '../services/footballstats.service';
import { FootBallStatsDTO } from '../models/DTOs/FootBallStatsDTO';
import { Observable } from 'rxjs';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-football-stats',
  standalone: true,
  imports: [CommonModule, TranslateModule],
  templateUrl: './football-stats.component.html',
  styleUrl: './football-stats.component.scss'
})
export class FootballStatsComponent {
  footballData$?: Observable<FootBallStatsDTO[]>;
  constructor(private footBallService: FootballstatsService){}
  
    ngOnInit(): void {
      this.getStats();
    }
  
    getStats(){
      this.footballData$ = this.footBallService.getFootballStats();
    }
  
    formatTime(timestamp: number): string {
      const date = new Date(timestamp * 1000); 
      const hours = date.getHours().toString().padStart(2, '0'); // Stunden im Format 'hh'
      const minutes = date.getMinutes().toString().padStart(2, '0'); // Minuten im Format 'mm'
      return `${hours}:${minutes}`;
    }
}
