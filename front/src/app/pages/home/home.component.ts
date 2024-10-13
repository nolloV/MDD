import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.checkUserAuthentication();
  }

  checkUserAuthentication(): void {
    const token = localStorage.getItem('token');
    if (token) {
      this.router.navigate(['/articles']);
    }
  }

  navigateToLogin(): void {
    this.router.navigate(['/login']); // Redirige vers la page de connexion
  }

  navigateToRegister(): void {
    this.router.navigate(['/register']); // Redirige vers la page d'inscription
  }
}