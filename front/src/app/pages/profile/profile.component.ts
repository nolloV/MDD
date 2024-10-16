import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/User';
import { Theme } from 'src/app/models/theme';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    user: User = { id: 0, username: '', email: '', subscribedThemes: [] }; // Initialiser l'utilisateur avec des valeurs par défaut
    subscriptions: Theme[] = [];
    errorMessage: string | null = null;

    constructor(
        private router: Router,
        private authService: AuthService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.loadUserData();
    }

    loadUserData(): void {
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.getSubscribedThemes(userId).subscribe(
                (user: User) => {
                    this.user = user;
                    this.subscriptions = user.subscribedThemes;
                },
                error => {
                    this.errorMessage = 'Erreur lors de la récupération des informations utilisateur.';
                    console.error(this.errorMessage, error);
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
            console.error(this.errorMessage);
        }
    }

    saveChanges(): void {
        if (this.user) {
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
    }

    logout(): void {
        this.authService.logout();
        this.router.navigate(['']);
    }

    unsubscribe(themeId: number): void {
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.unsubscribeFromTheme(userId, themeId).subscribe(
                (user: User) => {
                    this.subscriptions = user.subscribedThemes;
                    alert('Désabonnement réussi !');
                },
                error => {
                    this.errorMessage = 'Erreur lors du désabonnement du thème.';
                    console.error(this.errorMessage, error);
                }
            );
        }
    }
}