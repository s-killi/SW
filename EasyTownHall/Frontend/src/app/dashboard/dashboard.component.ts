import { Component, OnInit } from '@angular/core';
import { WeatherComponent } from "../weather/weather.component";
import { NewsService } from '../services/news.service';
import { NewsArticle } from '../models/newsArticle';
import { Observable } from 'rxjs';
import { CommonModule, DatePipe } from '@angular/common';
import { NinaService } from '../services/nina.service';
import { CovidTickerInfoDTO } from '../models/DTOs/CovidTickerInfoDTO';
import { TranslateModule } from '@ngx-translate/core';
import { FootballStatsComponent } from '../football-stats/football-stats.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [WeatherComponent, DatePipe, CommonModule, TranslateModule, FootballStatsComponent],
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.scss'
})
export class DashboardComponent implements OnInit{
  allArticles$? : Observable<NewsArticle[]>;
  covidData: CovidTickerInfoDTO[] = [];
  constructor(private newsService: NewsService, private nina: NinaService){
    this.getRecentArticles();
  }

  ngOnInit(): void {
    this.nina.getCovidDataFullInfo().subscribe(
      (data)=>{
        this.covidData = data;
      }
    )
  }

  getRecentArticles(): void{
    this.allArticles$ = this.newsService.getRecentArticles();
  }


  getTeaserHeader(title: string, maxLength: number): string{
    const s = title.replace("<p>", "").split("</p>")[0]
    return s.length > maxLength ? s.substring(0, maxLength-3) + "..." : s;

  }
}
