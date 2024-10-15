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
        console.log('Storing token:', token); // Ajout du log
        localStorage.setItem(this.tokenKey, token);
    }

    getToken(): string | null {
        const token = localStorage.getItem(this.tokenKey);
        console.log('Retrieved token:', token); // Ajout du log
        return token;
    }

    getUserInfoFromToken(): any {
        const token = this.getToken();
        if (token) {
            try {
                return jwtDecode(token);
            } catch (error) {
                console.error('Error decoding token:', error); // Ajout du log
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
        console.log('User info from token:', userInfo); // Ajout du log
        return userInfo ? userInfo.id : null;
    }

    logout(): void {
        localStorage.removeItem(this.tokenKey);
    }

    updateUser(user: { username: string; email: string }): Observable<any> {
        const token = this.getToken();
        console.log('Using token for update:', token); // Ajout du log
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}` // Inclure le token JWT dans les en-têtes de la requête
        });
        return this.http.put<any>('http://localhost:8080/users/me', user, { headers }); // Modification de l'URL
    }
}