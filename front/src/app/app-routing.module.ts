import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ThemeListComponent } from './pages/theme-list/theme-list.component';
import { LoginComponent } from './pages/login/login.component'; // Import LoginComponent
import { RegisterComponent } from './pages/register/register.component'; // Import RegisterComponent
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';

const routes: Routes = [
  { path: '', component: HomeComponent }, // Route vers Home
  { path: 'themes', component: ThemeListComponent }, // Route vers la liste des thèmes
  { path: 'login', component: LoginComponent }, // Route vers Login
  { path: 'register', component: RegisterComponent }, // Route vers Register
  { path: 'articles', component: ArticleListComponent }, // Route pour la liste des articles
  { path: 'article/:id', component: ArticleDetailComponent }, // Route pour la page de détail de l'article
  { path: '**', redirectTo: '/articles' }, // Pour toute autre route non définie, rediriger vers la liste des articles
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }
