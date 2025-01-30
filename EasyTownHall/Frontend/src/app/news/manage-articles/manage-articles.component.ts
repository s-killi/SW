import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { NewsArticle } from '../../models/newsArticle';
import { filter, Observable } from 'rxjs';
import { NewsService } from '../../services/news.service';
import { NavigationEnd, Router } from '@angular/router';
import { ArticlefilterPipe } from '../../pipes/articlefilter.pipe';
import { FormControl, FormGroup, ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { ErrorService } from '../../services/error.service';
import { ErrorDisplayComponent } from '../../error-display/error-display.component';
import { TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-manage-articles',
  standalone: true,
  imports: [CommonModule, ArticlefilterPipe, ReactiveFormsModule, ErrorDisplayComponent, TranslateModule],
  templateUrl: './manage-articles.component.html',
  styleUrls: [
    '../news/news.component.scss',
    './manage-articles.component.scss'
  ]
})
export class ManageArticlesComponent {

  allArticles? : Observable<NewsArticle[]>;
  allOrderedArticles? : NewsArticle[];
  searchtext? : any;
  filterControl = new FormControl('');
  orderForm: FormGroup;
  orderState? : boolean = false; // false=asc, true=desc

  constructor(
    private newsService: NewsService, 
    private router: Router, 
    public authService: AuthService, 
    public errorService: ErrorService,
    private fb: FormBuilder
  ) {
    this.newsService.refreshArticles(this.newsService.getAll());
    this.allArticles = this.newsService.getArticles();

    this.newsService.getAllOrdered('createdAt', 'desc').subscribe(articles => {
      this.allOrderedArticles = articles;
    });

    this.orderForm = this.fb.group({
      dropdownControl: ['createdAt'] // Default-Wert
    });
  }

  ngOnInit(): void {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe(() => {
      this.newsService.getAllOrdered('createdAt', this.orderState ? 'desc' : 'asc').subscribe(articles => {
        this.allOrderedArticles = articles;
      });
    });
  }

  changeOrder(): void {
    this.orderState = !this.orderState;
    this.onSubmit(); // Trigger Sortierung mit aktualisiertem Zustand
  }

  onSubmit(): void {
    const orderBy = this.orderForm.value.dropdownControl;
    const direction = this.orderState ? 'desc' : 'asc';

    this.newsService.getAllOrdered(orderBy, direction).subscribe({
      next: articles => {
        this.allOrderedArticles = articles;
        console.log(this.allOrderedArticles);
      },
      error: error => {
        console.error('Error loading ordered articles', error);
      }
    });
  }

  deleteArticle(id: number): void {
    this.newsService.deleteArticle(id).subscribe({
      next: () => {
        this.newsService.getAllOrdered('createdAt', this.orderState ? 'desc' : 'asc').subscribe(articles => {
          this.allOrderedArticles = articles;
        });
      },
      error: error => {
        console.error('Error deleting article:', error);
      }
    });
  }

  updateArticle(id: number): void {
    this.router.navigate(['/updateArticle', id]);
  }

  orderedArticlesEmpty(): boolean {
    return !this.allOrderedArticles || this.allOrderedArticles.length === 0;
  }
}
