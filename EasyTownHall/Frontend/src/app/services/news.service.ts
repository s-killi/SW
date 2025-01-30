import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, ReplaySubject, Subject, switchMap, takeUntil } from 'rxjs';
import { NewsArticle } from '../models/newsArticle';
import { environment } from '../../environments/environment';
import { TestService } from './test.service';

@Injectable({
  providedIn: 'root'
})
export class NewsService {
  
  constructor(private http: HttpClient, private testService: TestService) {}

  allArticles = new ReplaySubject<NewsArticle[]>(1);
  ngUnsubscribe$ = new Subject<void>();
  
  getArticleById(id: number): Observable<NewsArticle>{
    return this.http.get<NewsArticle>(`${environment.baseUrl}news/getArticle/${id}`);
  }

  getAll(): Observable<NewsArticle[]>{
    return this.http.get<NewsArticle[]>(environment.baseUrl + 'news/getAll');
  }

  getAllOrdered(orderBy: string = 'createdAt', direction: string = 'asc'): Observable<NewsArticle[]> {
    return this.http.get<NewsArticle[]>(`${environment.baseUrl}news/getAllOrdered?orderBy=${orderBy}&direction=${direction}`);
}

  getArticles(): Observable<NewsArticle[]> {
    return this.allArticles.asObservable();
  }

  getRecentArticles(): Observable<NewsArticle[]>{
    return this.http.get<NewsArticle[]>(environment.baseUrl + 'news/getRecent');
  }

  // user wird der eingeloggte ServiceWorker später genommen. Fürs erste ein TestModel User 
  addArticle(article: Partial<NewsArticle>): Observable<NewsArticle> {
    return this.http.post<NewsArticle>(`${environment.baseUrl}news/addArticle`, article);
  }
  
  deleteArticle(id: number): Observable<boolean> {
    return this.http.delete<boolean>(`${environment.baseUrl}news/deleteArticle/${id}`);
  } 

  updateArticle(id: number, article: Partial<NewsArticle>): Observable<NewsArticle>{
    let response = this.http.put<NewsArticle>(`${environment.baseUrl}news/updateArticle/${id}`, article);
    this.refreshArticles(response);
    return response;
  }

  refreshArticles<T>(observable: Observable<T>): void {
    observable.pipe(
      takeUntil(this.ngUnsubscribe$),
      switchMap(() => this.getAll())
    ).subscribe(data => {this.allArticles.next(data);});
  }

  addSubscription(userid: number): Observable<boolean>{
    return this.http.get<boolean>(`${environment.baseUrl}news/subscribe/${userid}`)
  }

  unsubscribe(userid: number): Observable<any>{
    return this.http.get(`${environment.baseUrl}news/unsubscribe/${userid}`)
  }

  isSubbed(userid: number): Observable<boolean>{
    return this.http.get<boolean>(`${environment.baseUrl}news/isSubbed/${userid}`);
  }

  sendMails():void {
    this.http.get(`${environment.baseUrl}news/sendMails`).subscribe({
      next: () => {
        alert('E-Mails wurden erfolgreich verschickt!');
      },
      error: (err) => {
        console.error('Fehler beim Senden der E-Mail:', err);
        alert('E-Mails konnten nicht verschickt werden.');
      }
    });
  }

  ngOnDestroy(): void {
    this.ngUnsubscribe$.next();
    this.ngUnsubscribe$.complete();
  }

}
