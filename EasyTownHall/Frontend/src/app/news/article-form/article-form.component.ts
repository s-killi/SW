import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ArticleCategory } from '../../models/enums/articleCategory';
import { ArticleCategoryDisplayName } from '../../mapper/ArticleCategoryDisplayName';
import { CommonModule } from '@angular/common';
import { NewsArticle } from '../../models/newsArticle';
import { NewsService } from '../../services/news.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-article-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, TranslateModule],
  templateUrl: './article-form.component.html',
  styleUrl: './article-form.component.scss'
})

export class ArticleFormComponent {
  isEditMode = false;
  articleId?: number;

  constructor(private newsService: NewsService, private route: ActivatedRoute, private router: Router, private translate: TranslateService) {}

  articleForm = new FormGroup({    
    title: new FormControl('', Validators.required),
    content: new FormControl(),
    category: new FormControl('', Validators.required),
    author: new FormControl('', Validators.required),
    isPublished: new FormControl(false, Validators.required)
  });

  categories = ArticleCategoryDisplayName;
  categoryTypes = Object.values(ArticleCategory);

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.articleId = +params['id'];
      if (this.articleId) {
        this.isEditMode = true;
        this.loadArticle();
      }
    });
  }

  onSubmit(): void{
    if(this.articleForm.valid){
      let article = this.checkArticleForm();
  
      if(this.isEditMode && this.articleId){
        this.newsService.updateArticle(this.articleId, article).subscribe({
          next:()=>{
            this.isEditMode = false;
          },
          error:error => console.log('Saving article failed', error)
        });
      }
      else{
        this.newsService.addArticle(article).subscribe({
          next: () => {
            this.newsService.refreshArticles(this.newsService.getAll());
          },
          error:error => console.log('Saving article failed', error)
        });
      }
      this.router.navigate(['/news/editArticles']);
    }
  }

  checkArticleForm(): Partial<NewsArticle> {
    let article: Partial<NewsArticle> = {
      title : this.articleForm.value.title ?? '',
      content : this.articleForm.value.content ?? '',
      category : this.articleForm.value.category as ArticleCategory ?? '',
      author: this.articleForm.value.author ?? '',
      published :  this.articleForm.value.isPublished ?? false,
    };
    return article;
}

loadArticle(): void {
  if (this.articleId) {
    this.newsService.getArticleById(this.articleId).subscribe(article => {
      this.articleForm.patchValue({
        title: article.title,
        content: article.content,
        category: article.category,
        author: article.author,
        isPublished: article.published,
      });
    });
  }
}

ngOnDestroy(): void {
  this.isEditMode = false;
}
}
