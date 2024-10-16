import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

    // Le constructeur injecte le service AuthService
    constructor(private authService: AuthService) { }

    // La méthode intercept est utilisée pour intercepter les requêtes HTTP sortantes
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        // Récupère le token d'authentification depuis AuthService
        const token = this.authService.getToken();

        // Si un token est présent, clone la requête et ajoute le token dans les en-têtes
        if (token) {
            const cloned = req.clone({
                headers: req.headers.set('Authorization', `Bearer ${token}`)
            });
            // Passe la requête clonée avec le token au prochain gestionnaire
            return next.handle(cloned);
        } else {
            // Si aucun token n'est présent, passe la requête originale au prochain gestionnaire
            return next.handle(req);
        }
    }
}