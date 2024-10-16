// front/src/app/services/user.service.ts
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
        console.log('Using token for headers:', token);
        return new HttpHeaders({
            'Authorization': `Bearer ${token}`
        });
    }

    subscribeToTheme(userId: number, themeId: number): Observable<User> {
        const headers = this.getAuthHeaders();
        console.log('Headers for subscribe:', headers);
        return this.http.post<User>(`${this.baseUrl}/${userId}/subscribe/${themeId}`, {}, { headers });
    }

    unsubscribeFromTheme(userId: number, themeId: number): Observable<User> {
        const headers = this.getAuthHeaders();
        console.log('Headers for unsubscribe:', headers);
        return this.http.post<User>(`${this.baseUrl}/${userId}/unsubscribe/${themeId}`, {}, { headers });
    }

    getSubscribedThemes(userId: number): Observable<User> {
        const headers = this.getAuthHeaders();
        console.log('Headers for getSubscribedThemes:', headers);
        return this.http.get<User>(`${this.baseUrl}/${userId}`, { headers });
    }
}