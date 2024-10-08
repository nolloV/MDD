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

    getThemes(): Observable<Theme[]> {
        return this.http.get<Theme[]>(this.apiUrl);
    }

    addTheme(theme: Theme): Observable<Theme> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<Theme>(this.apiUrl, theme, { headers });
    }

    updateTheme(id: number, theme: Theme): Observable<Theme> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.put<Theme>(`${this.apiUrl}/${id}`, theme, { headers });
    }

    deleteTheme(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }
}