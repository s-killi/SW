import { Routes } from '@angular/router';
import { LoginFormComponent } from './login-form/login-form.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { TestComponent } from './test/test.component';
import { TestHelloComponent } from './test-hello/test-hello.component';
import { ProfilComponent } from './profil/profil.component';

import { NewsComponent } from './news/news/news.component';
import { ArticleFormComponent } from './news/article-form/article-form.component';
import { ApplicationFormService } from './services/application-form.service';
import { AdminFormListComponent } from './admin-form-list/admin-form-list.component';
import { CitizenFormListComponent } from './citizen-form-list/citizen-form-list.component';
import { FillFormComponent } from './fill-form/fill-form.component';

import { UserRoleFormComponent } from './user-role-form/user-role-form.component';
import { AddFormComponent } from './add-form/add-form.component';

import { ManageArticlesComponent } from './news/manage-articles/manage-articles.component';
import { ApplicationDashboardComponent } from './application-dashboard/application-dashboard.component';
import { ApplicationReviewComponent } from './application-review/application-review.component';
import { AppointmentsComponent } from './appointments/appointments/appointments.component';
import { AppointmentFormComponent } from './appointments/appointment-form/appointment-form.component';
import { AboutUsComponent } from './about-us/about-us.component';

export const routes: Routes = [
    {path:'', component:DashboardComponent},
    {path:'home', redirectTo:'', pathMatch: 'full'},
    {path:'login', component:LoginFormComponent},
    {path:'application', component:AdminFormListComponent},
    {path:'application/citizen', component: CitizenFormListComponent},
    {path:'application/dashboard', component: ApplicationDashboardComponent},
    {path:'application/service/add', component: AddFormComponent},
    {path:'application/service/review/:id', component: ApplicationReviewComponent},
    {path:'application/service/edit/:id', component: AddFormComponent},
    {path:'application/citizen/fill/:id', component: FillFormComponent},
    {path:'projects', component:TestComponent},
    {path:'profil/:id', component:ProfilComponent},
    {path:'about', component:AboutUsComponent},
    {path:'news', component:NewsComponent},
    {path: 'newArticle', component: ArticleFormComponent},
    {path: 'updateArticle/:id', component: ArticleFormComponent },
    {path:'roles', component:UserRoleFormComponent},
    {path: 'news/editArticles', component: ManageArticlesComponent},
    {path: 'appointments', component: AppointmentsComponent},
    {path: 'appointments/new', component: AppointmentFormComponent},
    {path: 'appointments/update/:id', component: AppointmentFormComponent}
];
0
