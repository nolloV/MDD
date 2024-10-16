import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { faBars, faUser } from '@fortawesome/free-solid-svg-icons'; // Importer les icônes de FontAwesome

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
    faBars = faBars; // Déclarer l'icône de menu
    faUser = faUser; // Déclarer l'icône d'utilisateur
    menuOpen: boolean = false; // Variable pour gérer l'état du menu mobile

    constructor(public router: Router) { } // Injecter le service Router

    // Méthode pour ouvrir/fermer le menu mobile
    toggleMenu(): void {
        this.menuOpen = !this.menuOpen; // Inverser l'état du menu
    }

    // Vérifier si le menu doit être caché (Login/Register)
    shouldHideNav(): boolean {
        return this.router.url === '/login' || this.router.url === '/register'; // Cacher le menu si l'URL est /login ou /register
    }
}