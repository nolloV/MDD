import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  // Le constructeur injecte les services AuthService et Router
  constructor(private authService: AuthService, private router: Router) { }

  // La méthode canActivate est utilisée pour déterminer si une route peut être activée
  canActivate(): boolean {
    // Vérifie si un token d'authentification est présent
    if (this.authService.getToken()) {
      // Si un token est présent, l'accès est autorisé
      return true;
    } else {
      // Sinon, l'utilisateur est redirigé vers la page d'accueil
      this.router.navigate(['/']);
      return false;
    }
  }
}