import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { jwtDecode } from 'jwt-decode';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private baseUrl = 'http://localhost:8080/auth';
    private tokenKey = 'authToken';

    constructor(private http: HttpClient) { }

    login(credentials: { identifier: string; password: string }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<any>(`${this.baseUrl}/login`, credentials, { headers }).pipe(
            tap(response => {
                if (response && response.token) {
                    this.setToken(response.token);
                }
            })
        );
    }

    register(user: { username: string; email: string; password: string }): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/register`, user);
    }

    setToken(token: string): void {
        localStorage.setItem(this.tokenKey, token);
    }

    getToken(): string | null {
        return localStorage.getItem(this.tokenKey);
    }

    getUserInfoFromToken(): any {
        const token = this.getToken();
        if (token) {
            try {
                return jwtDecode(token);
            } catch (error) {
                return null;
            }
        }
        return null;
    }

    getUsername(): string | null {
        const userInfo = this.getUserInfoFromToken();
        return userInfo ? userInfo.username : null;
    }

    getUserId(): number | null {
        const userInfo = this.getUserInfoFromToken();
        return userInfo ? userInfo.id : null;
    }

    logout(): void {
        localStorage.removeItem(this.tokenKey);
    }

    updateUser(user: { username: string; email: string }): Observable<any> {
        const token = this.getToken();
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Inclure le token JWT dans les en-têtes de la requête
        });
        return this.http.put<any>('http://localhost:8080/users/me', user, { headers }); // Modification de l'URL
    }
}