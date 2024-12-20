import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Article } from '../models/article';
import { Comment } from '../models/comment';

@Injectable({
    providedIn: 'root'
})
export class ArticleService {
    private baseUrl = 'http://localhost:8080/articles'; // URL de base de l'API backend pour les articles et les commentaires

    constructor(private http: HttpClient) { }

    // Méthode pour obtenir les en-têtes d'authentification
    private getAuthHeaders(): HttpHeaders {
        const token = localStorage.getItem('authToken');
        return new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        });
    }

    // Récupérer tous les articles
    getArticles(): Observable<Article[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<Article[]>(this.baseUrl, { headers });
    }

    // Ajouter un nouvel article
    addArticle(article: Article): Observable<Article> {
        const headers = this.getAuthHeaders();
        return this.http.post<Article>(this.baseUrl, article, { headers });
    }

    // Récupérer un article par ID
    getArticleById(id: number): Observable<Article> {
        const headers = this.getAuthHeaders();
        return this.http.get<Article>(`${this.baseUrl}/${id}`, { headers });
    }

    // Récupérer les commentaires d'un article par ID
    getCommentsByArticleId(articleId: number): Observable<Comment[]> {
        const headers = this.getAuthHeaders();
        return this.http.get<Comment[]>(`${this.baseUrl}/${articleId}/comments`, { headers });
    }

    // Ajouter un commentaire à un article
    addCommentToArticle(articleId: number, comment: Comment): Observable<Comment> {
        const headers = this.getAuthHeaders();
        return this.http.post<Comment>(`${this.baseUrl}/${articleId}/comments`, comment, { headers });
    }
}