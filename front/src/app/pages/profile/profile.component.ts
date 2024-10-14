import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service'; // Importer le service d'authentification

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    user: any = {}; // Initialiser l'utilisateur avec un objet vide
    subscriptions: any[] = []; // Initialiser les abonnements avec un tableau vide
    errorMessage: string | null = null;

    constructor(private router: Router, private authService: AuthService) { }

    ngOnInit(): void {
        this.loadUserData();
    }

    loadUserData(): void {
        const userInfo = this.authService.getUserInfoFromToken();
        if (userInfo) {
            this.user = {
                username: userInfo.username,
                email: userInfo.email
            };
            if (userInfo.subscriptions) {
                this.subscriptions = userInfo.subscriptions;
            }
        } else {
            this.errorMessage = 'Token non trouvé ou invalide';
            console.error(this.errorMessage);
        }
    }

    saveChanges(): void {
        this.authService.updateUser(this.user).subscribe(
            response => {
                alert('Informations sauvegardées avec succès.');
            },
            error => {
                this.errorMessage = 'Erreur lors de la sauvegarde des informations.';
                console.error(this.errorMessage, error);
            }
        );
    }

    logout(): void {
        this.authService.logout(); // Utiliser la méthode logout du service AuthService
        this.router.navigate(['']);
    }

    unsubscribe(themeId: number): void {
        this.subscriptions = this.subscriptions.filter(subscription => subscription.theme.id !== themeId);
        alert('Désinscrit du thème avec ID : ' + themeId);
    }
}