import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { } // Injecter le service Router

  // Méthode appelée lors de l'initialisation du composant
  ngOnInit(): void {
    this.checkUserAuthentication(); // Vérifier l'authentification de l'utilisateur
  }

  // Vérifier si l'utilisateur est authentifié
  checkUserAuthentication(): void {
    const token = localStorage.getItem('token'); // Récupérer le token du localStorage
    if (token) {
      this.router.navigate(['/articles']); // Rediriger vers la page des articles si l'utilisateur est authentifié
    }
  }

  // Rediriger vers la page de connexion
  navigateToLogin(): void {
    this.router.navigate(['/login']); // Redirige vers la page de connexion
  }

  // Rediriger vers la page d'inscription
  navigateToRegister(): void {
    this.router.navigate(['/register']); // Redirige vers la page d'inscription
  }
}