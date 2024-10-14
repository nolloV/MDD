import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ThemeListComponent } from './pages/theme-list/theme-list.component';
import { LoginComponent } from './pages/login/login.component'; // Import LoginComponent
import { RegisterComponent } from './pages/register/register.component'; // Import RegisterComponent
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import { ArticleCreateComponent } from './pages/article-create/article-create.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { AuthGuard } from './guards/auth.guard'; // Importer le guard d'authentification

// Définir les routes de l'application
const routes: Routes = [
  { path: '', component: HomeComponent }, // Route pour la page Home (sans le header)
  { path: 'themes', component: ThemeListComponent, canActivate: [AuthGuard] }, // Route vers la liste des thèmes
  { path: 'login', component: LoginComponent }, // Route vers la page de connexion
  { path: 'register', component: RegisterComponent }, // Route vers la page d'inscription
  { path: 'articles', component: ArticleListComponent, canActivate: [AuthGuard] }, // Route pour la liste des articles
  { path: 'articles/create', component: ArticleCreateComponent, canActivate: [AuthGuard] }, // Route pour la création d'un article
  { path: 'article/:id', component: ArticleDetailComponent, canActivate: [AuthGuard] }, // Route pour afficher un article en fonction de l'ID
  { path: 'profile', component: ProfileComponent, canActivate: [AuthGuard] }, // Route pour la page de profil
  { path: '**', redirectTo: '/articles' }, // Pour toute autre route non définie, rediriger vers la liste des articles
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule { }