import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { LoginResponse } from '../models/auth.models'; // Import des modèles depuis auth.models.ts
import { User } from '../models/User'; // Import du modèle User
import { ChangePasswordResponse } from '../models/change-password-response.model'; // Import du modèle ChangePasswordResponse
import { PasswordChangeRequest } from '../models/password-change-request.model'; // Import du modèle PasswordChangeRequest
import { jwtDecode } from 'jwt-decode'; // Utilisation de jwtDecode pour décoder le token JWT

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private baseUrl = 'http://localhost:8080/auth'; // URL de base pour les appels à l'API d'authentification
    private tokenKey = 'authToken'; // Clé utilisée pour stocker le token dans localStorage

    constructor(private http: HttpClient) { }

    // Méthode pour se connecter avec des identifiants (email ou username et mot de passe)
    login(credentials: { identifier: string; password: string }): Observable<LoginResponse> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<LoginResponse>(`${this.baseUrl}/login`, credentials, { headers }).pipe(
            tap(response => {
                // Si le token est renvoyé dans la réponse, le stocker dans localStorage
                if (response && response.token) {
                    this.setToken(response.token);
                }
            })
        );
    }

    // Méthode pour enregistrer un nouvel utilisateur
    register(user: { username: string; email: string; password: string }): Observable<User> {
        return this.http.post<User>(`${this.baseUrl}/register`, user);
    }

    // Stocker le token JWT dans localStorage
    setToken(token: string): void {
        localStorage.setItem(this.tokenKey, token);
    }

    // Récupérer le token JWT depuis localStorage
    getToken(): string | null {
        return localStorage.getItem(this.tokenKey);
    }

    // Décoder le token JWT pour extraire les informations de l'utilisateur
    getUserInfoFromToken(): { id: number; username: string } | null {
        const token = this.getToken();
        if (token) {
            try {
                return jwtDecode<{ id: number; username: string }>(token); // Décodage du token JWT
            } catch (error) {
                return null; // Si le token est invalide ou corrompu
            }
        }
        return null;
    }

    // Récupérer le nom d'utilisateur depuis le token JWT
    getUsername(): string | null {
        const userInfo = this.getUserInfoFromToken();
        return userInfo ? userInfo.username : null;
    }

    // Récupérer l'ID utilisateur depuis le token JWT
    getUserId(): number | null {
        const userInfo = this.getUserInfoFromToken();
        return userInfo ? userInfo.id : null;
    }

    // Déconnexion : supprimer le token JWT du localStorage
    logout(): void {
        localStorage.removeItem(this.tokenKey);
    }

    // Mettre à jour les informations utilisateur (username et email)
    updateUser(user: { username: string; email: string }): Observable<User> {
        const token = this.getToken();
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Ajouter le token JWT dans l'en-tête Authorization
        });
        return this.http.put<User>('http://localhost:8080/users/me', user, { headers }); // URL de l'endpoint pour mettre à jour les infos utilisateur
    }

    // Changer le mot de passe de l'utilisateur
    changePassword(passwordData: PasswordChangeRequest): Observable<ChangePasswordResponse> {
        const token = this.getToken();
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Ajouter le token JWT dans l'en-tête Authorization
        });

        return this.http.post<ChangePasswordResponse>(`${this.baseUrl}/change-password`, passwordData, { headers });
    }
}