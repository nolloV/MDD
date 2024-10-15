import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { Theme } from '../../models/theme';
import { AuthService } from '../../services/auth.service';
import { UserService } from '../../services/user.service';
import { User } from '../../models/User';

@Component({
    selector: 'app-theme-list',
    templateUrl: './theme-list.component.html',
    styleUrls: ['./theme-list.component.scss']
})
export class ThemeListComponent implements OnInit {
    themes: Theme[] = [];
    subscribedThemes: string[] = []; // Changement de type pour correspondre à la réponse de l'API
    errorMessage: string | null = null;

    constructor(
        private themeService: ThemeService,
        private authService: AuthService,
        private userService: UserService
    ) { }

    ngOnInit(): void {
        this.loadThemes();
        this.loadSubscribedThemes();
    }

    loadThemes(): void {
        this.themeService.getThemes().subscribe(
            themes => this.themes = themes,
            error => this.errorMessage = 'Erreur lors du chargement des thèmes.'
        );
    }

    loadSubscribedThemes(): void {
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.getSubscribedThemes(userId).subscribe(
                (user: User) => {
                    console.log('Subscribed themes:', user.subscribedThemes);
                    this.subscribedThemes = user.subscribedThemes;
                },
                error => {
                    this.errorMessage = 'Erreur lors du chargement des thèmes abonnés.';
                    console.error('Erreur lors du chargement des thèmes abonnés:', error);
                }
            );
        }
    }

    isSubscribed(themeTitle: string): boolean { // Changement de type pour correspondre à la réponse de l'API
        return this.subscribedThemes.includes(themeTitle);
    }

    subscribeToTheme(themeTitle: string): void { // Changement de type pour correspondre à la réponse de l'API
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.subscribeToTheme(userId, themeTitle).subscribe(
                () => {
                    alert('Abonnement réussi !');
                    this.loadSubscribedThemes();
                },
                error => {
                    this.errorMessage = 'Erreur lors de l\'abonnement au thème.';
                    console.error('Erreur lors de l\'abonnement au thème:', error);
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
            console.error('Utilisateur non authentifié.');
        }
    }

    unsubscribeFromTheme(themeTitle: string): void { // Changement de type pour correspondre à la réponse de l'API
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.unsubscribeFromTheme(userId, themeTitle).subscribe(
                () => {
                    alert('Désabonnement réussi !');
                    this.loadSubscribedThemes();
                },
                error => {
                    this.errorMessage = 'Erreur lors du désabonnement du thème.';
                    console.error('Erreur lors du désabonnement du thème:', error);
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
            console.error('Utilisateur non authentifié.');
        }
    }
}