import { Component, numberAttribute } from '@angular/core';
import { NewsService } from '../../services/news.service';
import { Observable, of, ReplaySubject, Subject, switchMap, takeUntil } from 'rxjs';
import { NewsArticle } from '../../models/newsArticle';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { ArticleCategoryDisplayName } from '../../mapper/ArticleCategoryDisplayName';
import { Router } from '@angular/router';
import { ArticlefilterPipe } from "../../pipes/articlefilter.pipe";
import { AuthService } from '../../services/auth.service';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-news',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, ArticlefilterPipe, TranslateModule],
  templateUrl: './news.component.html',
  styleUrl: './news.component.scss'
})
export class NewsComponent {

  searchtext? : any;
  categories = ArticleCategoryDisplayName;
  filterControl = new FormControl('');
  allArticles? : Observable<NewsArticle[]>;
  allOrderedArticles? : NewsArticle[];
  orderState? : boolean = false; //false= asc, true=desc
  isSubbed? : boolean = false;
  orderForm: FormGroup;

  constructor(private newsService: NewsService, private router: Router, public authService: AuthService, private fb: FormBuilder, private translate: TranslateService){
    this.newsService.refreshArticles(this.newsService.getAll());
    this.allArticles = this.newsService.getArticles();
    this.newsService.getAllOrdered('createdAt', 'desc').subscribe(articles => {
      this.allOrderedArticles = articles;
  });
    console.log("ordered articles", this.allOrderedArticles);

    const userId = this.authService.getId();
    this.newsService.isSubbed(userId).subscribe((success) => {
      this.isSubbed = success;
    });

    this.orderForm = this.fb.group({
      dropdownControl: ['createdAt'] // Default-Wert
    });
  }
  
  ngOnInit(): void {
    const token = this.authService.getToken();
    if (token) {
      this.authService.forceReloadRoles(token); //Für prüfen der Rolle in einem Component Rollen aktualisieren!
    }
  }

  onSubmit() {
    console.log('Ausgewählter Wert:', this.orderForm.value.dropdownControl);
    let direction = 'asc';
    if(this.orderState){
      direction = 'desc';
    }
    let orderBy = this.orderForm.value.dropdownControl;
    this.newsService.getAllOrdered(orderBy, direction).subscribe(
      {
        next: articles => {
          this.allOrderedArticles = articles;
          console.log("ordered articles", this.allOrderedArticles);
      },
      error: error =>{
        console.log("Error loading ordered articles", error);
      }

  });
  }

  subscribeToNewsLetter(): void{
    const userId = this.authService.getId();
    this.newsService.addSubscription(userId).subscribe({
      next: () => {
        this.newsService.isSubbed(userId).subscribe((success) => {
          this.isSubbed = success;
      });
    }});
  }

  unsubscribe(): void{
    const userId = this.authService.getId();
    this.newsService.unsubscribe(userId).subscribe({
      next: () => {
        this.newsService.isSubbed(userId).subscribe((success) => {
          this.isSubbed = success;
      });
    }});
  }

  sendMails(): void{
    this.newsService.sendMails();
  }

  changeOrder(): void{
    this.orderState = !this.orderState;
  }

  orderedArticlesEmpty(): boolean{
    if(this.allOrderedArticles?.length == 0){
      return true;
    }
    return false;
  }

}
