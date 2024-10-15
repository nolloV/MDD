import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/User';

@Injectable({
    providedIn: 'root'
})
export class UserService {
    private apiUrl = 'http://localhost:8080/users';  // URL de l'API backend

    constructor(private http: HttpClient) { }

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
    }

    subscribeToTheme(userId: number, themeId: number): Observable<User> {
        const headers = this.getAuthHeaders();
        return this.http.post<User>(`${this.apiUrl}/${userId}/subscribe/${themeId}`, {}, { headers });
    }
}