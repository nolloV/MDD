import { Component, OnInit } from '@angular/core';
import { ThemeService } from '../../services/theme.service';
import { Theme } from '../../models/theme';
import { AuthService } from '../../services/auth.service'; // Importer AuthService
import { UserService } from '../../services/user.service'; // Importer UserService

@Component({
    selector: 'app-theme-list',
    templateUrl: './theme-list.component.html',
    styleUrls: ['./theme-list.component.scss']
})
export class ThemeListComponent implements OnInit {
    themes: Theme[] = [];
    errorMessage: string | null = null;

    constructor(
        private themeService: ThemeService,
        private authService: AuthService, // Injecter AuthService
        private userService: UserService // Injecter UserService
    ) { }

    ngOnInit(): void {
        this.loadThemes();
    }

    loadThemes(): void {
        this.themeService.getThemes().subscribe(
            themes => this.themes = themes,
            error => this.errorMessage = 'Erreur lors du chargement des thèmes.'
        );
    }

    subscribeToTheme(themeId: number): void {
        const userId = this.authService.getUserId(); // Récupérer l'ID de l'utilisateur connecté
        console.log('User ID:', userId); // Ajout du log
        if (userId) {
            this.userService.subscribeToTheme(userId, themeId).subscribe(
                (user) => {
                    console.log('Abonnement réussi !', user); // Log de confirmation
                    alert('Abonnement réussi !');
                },
                error => {
                    this.errorMessage = 'Erreur lors de l\'abonnement au thème.';
                    console.error('Erreur lors de l\'abonnement au thème:', error); // Log de l'erreur
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.';
            console.error('Utilisateur non authentifié.'); // Log de l'erreur
        }
    }
}