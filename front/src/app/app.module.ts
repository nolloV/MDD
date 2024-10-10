import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { MatButtonModule } from '@angular/material/button';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './pages/home/home.component';
import { ThemeListComponent } from './pages/theme-list/theme-list.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { ArticleListComponent } from './pages/article-list/article-list.component';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas, faSort } from '@fortawesome/free-solid-svg-icons';
import { CommonModule } from '@angular/common';
import { ArticleDetailComponent } from './pages/article-detail/article-detail.component';
import { ArticleCreateComponent } from './pages/article-create/article-create.component';
import { HeaderComponent } from './pages/header/header.component';
import { ProfileComponent } from './pages/profile/profile.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ThemeListComponent,
    LoginComponent,
    RegisterComponent, ArticleListComponent, ArticleDetailComponent, ArticleCreateComponent, HeaderComponent, ProfileComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatButtonModule,
    HttpClientModule,
    ReactiveFormsModule, FontAwesomeModule, FormsModule, CommonModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
  constructor(library: FaIconLibrary) {
    library.addIconPacks(fas); // Ajout des icônes solid (fas)
    library.addIcons(faSort); // Ajout explicite de l'icône 'sort'
  }
}