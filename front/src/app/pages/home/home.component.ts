import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  constructor(private router: Router) { }

  navigateToLogin(): void {
    this.router.navigate(['/login']); // Redirige vers la page de connexion
  }

  navigateToRegister(): void {
    this.router.navigate(['/register']); // Redirige vers la page d'inscription
  }
}
