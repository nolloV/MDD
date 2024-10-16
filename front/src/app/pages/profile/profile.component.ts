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
    saveSuccess: boolean = false; // Variable pour indiquer si la sauvegarde a été effectuée avec succès

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
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
        }
    }

    saveChanges(): void {
        if (this.user) {
            this.authService.updateUser(this.user).subscribe(
                response => {
                    this.saveSuccess = true; // Indiquer que la sauvegarde a été effectuée avec succès
                    alert('Informations sauvegardées avec succès. Veuillez vous reconnecter.');
                    this.logout(); // Déconnecter l'utilisateur pour qu'il puisse se reconnecter avec le nouveau token
                },
                error => {
                    this.errorMessage = 'Erreur lors de la sauvegarde des informations.';
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
                    // Désabonnement réussi
                },
                error => {
                    this.errorMessage = 'Erreur lors du désabonnement du thème.';
                }
            );
        }
    }
}