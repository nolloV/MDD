import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private baseUrl = 'http://localhost:8080/auth';

    constructor(private http: HttpClient) { }

    login(credentials: { identifier: string; password: string }): Observable<any> {
        const headers = new HttpHeaders({ 'Content-Type': 'application/json' });
        return this.http.post<any>(`${this.baseUrl}/login`, credentials, { headers });
    }

    register(user: { username: string; email: string; password: string }): Observable<any> {
        return this.http.post<any>(`${this.baseUrl}/register`, user);
    }
}