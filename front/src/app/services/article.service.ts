import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../models/article';
import { Comment } from '../models/comment';

@Injectable({
    providedIn: 'root'
})
export class ArticleService {
    private apiUrl = 'http://localhost:8080/articles'; // URL de l'API backend pour les articles
    private commentApiUrl = 'http://localhost:8080/articles'; // URL pour les commentaires d'articles

    constructor(private http: HttpClient) { }

    // Récupérer tous les articles
    getArticles(): Observable<Article[]> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<Article[]>(this.apiUrl, { headers });
    }

    // Ajouter un nouvel article
    addArticle(article: Article): Observable<Article> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        return this.http.post<Article>(this.apiUrl, article, { headers });
    }

    // Récupérer un article par ID
    getArticleById(id: number): Observable<Article> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<Article>(`${this.apiUrl}/${id}`, { headers });
    }

    // Récupérer les commentaires d'un article par ID
    getCommentsByArticleId(articleId: number): Observable<Comment[]> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        return this.http.get<Comment[]>(`${this.commentApiUrl}/${articleId}/comments`, { headers });
    }

    // Ajouter un commentaire à un article
    addCommentToArticle(articleId: number, comment: Comment): Observable<Comment> {
        const token = localStorage.getItem('authToken');
        const headers = new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
        return this.http.post<Comment>(`${this.commentApiUrl}/${articleId}/comments`, comment, { headers });
    }
}