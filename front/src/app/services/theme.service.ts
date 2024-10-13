import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Theme } from '../models/theme';

@Injectable({
    providedIn: 'root'
})
export class ThemeService {
    private apiUrl = 'http://localhost:8080/themes';  // URL de l'API backend

    constructor(private http: HttpClient) { }

    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('token');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
    }

    getThemes(): Observable<Theme[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<Theme[]>(this.apiUrl, { headers });
    }

    addTheme(theme: Theme): Observable<Theme> {
        const headers = this.getAuthHeaders();
        return this.http.post<Theme>(this.apiUrl, theme, { headers });
    }

    updateTheme(id: number, theme: Theme): Observable<Theme> {
        const headers = this.getAuthHeaders();
        return this.http.put<Theme>(`${this.apiUrl}/${id}`, theme, { headers });
    }

    deleteTheme(id: number): Observable<void> {
        const headers = this.getAuthHeaders();
        return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers });
    }
}