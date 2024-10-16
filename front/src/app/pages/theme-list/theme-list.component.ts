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
    subscribedThemes: Theme[] = [];
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
                    this.subscribedThemes = user.subscribedThemes;
                },
                error => {
                    this.errorMessage = 'Erreur lors du chargement des thèmes abonnés.';
                }
            );
        }
    }

    isSubscribed(themeId: number): boolean {
        return this.subscribedThemes.some(theme => theme.id === themeId);
    }

    subscribeToTheme(themeId: number): void {
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.subscribeToTheme(userId, themeId).subscribe(
                (user: User) => {
                    this.subscribedThemes = user.subscribedThemes;
                },
                error => {
                    this.errorMessage = 'Erreur lors de l\'abonnement au thème.';
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
        }
    }

    unsubscribeFromTheme(themeId: number): void {
        const userId = this.authService.getUserId();
        if (userId) {
            this.userService.unsubscribeFromTheme(userId, themeId).subscribe(
                (user: User) => {
                    this.subscribedThemes = user.subscribedThemes;
                },
                error => {
                    this.errorMessage = 'Erreur lors du désabonnement du thème.';
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
        }
    }
}