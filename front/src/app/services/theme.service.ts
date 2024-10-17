import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theme } from '../models/theme';

@Injectable({
    providedIn: 'root'
})
export class ThemeService {
    private apiUrl = 'http://localhost:8080/themes';  // URL de l'API backend pour les thèmes

    constructor(private http: HttpClient) { }

    // Méthode privée pour récupérer les en-têtes d'authentification avec le token JWT
    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('token'); // Récupérer le token JWT depuis le localStorage
        return new HttpHeaders({
            'Content-Type': 'application/json', // Définir le type de contenu comme JSON
            'Authorization': `Bearer ${token}`   // Ajouter le token JWT à l'en-tête Authorization
        });
    }

    // Récupérer la liste des thèmes
    getThemes(): Observable<Theme[]> {
        const headers = this.getAuthHeaders(); // Ajouter les en-têtes d'authentification
        return this.http.get<Theme[]>(this.apiUrl, { headers });
    }

    // Récupérer un thème par ID
    getThemeById(id: number): Observable<Theme> {
        const headers = this.getAuthHeaders(); // Ajouter les en-têtes d'authentification
        return this.http.get<Theme>(`${this.apiUrl}/${id}`, { headers });
    }

    // Ajouter un nouveau thème
    addTheme(theme: Theme): Observable<Theme> {
        const headers = this.getAuthHeaders(); // Ajouter les en-têtes d'authentification
        return this.http.post<Theme>(this.apiUrl, theme, { headers });
    }

    // Mettre à jour un thème existant
    updateTheme(id: number, theme: Theme): Observable<Theme> {
        const headers = this.getAuthHeaders(); // Ajouter les en-têtes d'authentification
        return this.http.put<Theme>(`${this.apiUrl}/${id}`, theme, { headers });
    }

    // Supprimer un thème
    deleteTheme(id: number): Observable<void> {
        const headers = this.getAuthHeaders(); // Ajouter les en-têtes d'authentification
        return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
    }
}