import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-not-found',
    templateUrl: './not-found.component.html',
    styleUrls: ['./not-found.component.scss']
})
export class NotFoundComponent {
    // Constructeur pour injecter le service Router
    constructor(private router: Router) { }

    // Méthode pour naviguer vers la page d'accueil
    navigateToHome(): void {
        this.router.navigate(['/']); // Redirige vers la page d'accueil
    }
}