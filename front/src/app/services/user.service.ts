import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';  // Importation du modèle User
import { Article } from '../models/article'; // Importation du modèle Article
import { AuthService } from './auth.service';  // Importation du service d'authentification pour récupérer le token

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private baseUrl = 'http://localhost:8080/users';  // URL de l'API backend pour les utilisateurs

    constructor(private http: HttpClient, private authService: AuthService) { }

    // Méthode privée pour obtenir les en-têtes d'authentification avec le token JWT
    private getAuthHeaders(): HttpHeaders {
        const token = this.authService.getToken();  // Récupération du token JWT via le service d'authentification
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`  // Ajout du token JWT dans l'en-tête Authorization
        });
    }

    // Abonnement de l'utilisateur à un thème
    subscribeToTheme(userId: number, themeId: number): Observable<User> {
        const headers = this.getAuthHeaders();  // Ajout des en-têtes d'authentification
        return this.http.post<User>(`${this.baseUrl}/${userId}/subscribe/${themeId}`, {}, { headers });  // Requête POST pour l'abonnement
    }

    // Désabonnement de l'utilisateur d'un thème
    unsubscribeFromTheme(userId: number, themeId: number): Observable<User> {
        const headers = this.getAuthHeaders();  // Ajout des en-têtes d'authentification
        return this.http.post<User>(`${this.baseUrl}/${userId}/unsubscribe/${themeId}`, {}, { headers });  // Requête POST pour le désabonnement
    }

    // Récupération des thèmes auxquels l'utilisateur est abonné
    getSubscribedThemes(userId: number): Observable<User> {
        const headers = this.getAuthHeaders();  // Ajout des en-têtes d'authentification
        return this.http.get<User>(`${this.baseUrl}/${userId}`, { headers });  // Requête GET pour obtenir les abonnements de l'utilisateur
    }

    // Nouvelle méthode pour récupérer les articles des thèmes abonnés
    getArticlesForSubscribedThemes(userId: number): Observable<Article[]> {
        const headers = this.getAuthHeaders();  // Ajout des en-têtes d'authentification
        return this.http.get<Article[]>(`${this.baseUrl}/${userId}/subscribed-articles`, { headers });  // Requête GET pour obtenir les articles des thèmes abonnés
    }
}