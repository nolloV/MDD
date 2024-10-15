import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';
import { Theme } from '../models/theme';
import { AuthService } from './auth.service';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private baseUrl = 'http://localhost:8080/users';

    constructor(private http: HttpClient, private authService: AuthService) { }

    private getAuthHeaders(): HttpHeaders {
        const token = this.authService.getToken();
        console.log('Using token for headers:', token); // Ajout du log
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    subscribeToTheme(userId: number, themeId: number): Observable<User> { // Utilisation de l'ID du thème
        const headers = this.getAuthHeaders();
        console.log('Headers for subscribe:', headers); // Ajout du log
        return this.http.post<User>(`${this.baseUrl}/${userId}/subscribe/${themeId}`, {}, { headers }); // Utilisation de l'ID du thème dans l'URL
    }

    unsubscribeFromTheme(userId: number, themeId: number): Observable<User> { // Utilisation de l'ID du thème
        const headers = this.getAuthHeaders();
        console.log('Headers for unsubscribe:', headers); // Ajout du log
        return this.http.post<User>(`${this.baseUrl}/${userId}/unsubscribe/${themeId}`, {}, { headers }); // Utilisation de l'ID du thème dans l'URL
    }

    getSubscribedThemes(userId: number): Observable<User> {
        const headers = this.getAuthHeaders();
        console.log('Headers for getSubscribedThemes:', headers); // Ajout du log
        return this.http.get<User>(`${this.baseUrl}/${userId}`, { headers }); // Correction de l'URL
    }
}