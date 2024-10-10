import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { faBars, faUser } from '@fortawesome/free-solid-svg-icons';

@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
    faBars = faBars;
    faUser = faUser;
    menuOpen: boolean = false; // Variable pour gérer l'état du menu mobile

    constructor(public router: Router) { }

    // Méthode pour ouvrir/fermer le menu mobile
    toggleMenu(): void {
        this.menuOpen = !this.menuOpen;
    }

    // Vérifier si le menu doit être caché (Login/Register)
    shouldHideNav(): boolean {
        return this.router.url === '/login' || this.router.url === '/register';
    }
}
