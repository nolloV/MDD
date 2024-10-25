import { Component, OnInit, OnDestroy } from '@angular/core'; // Importer OnDestroy
import { ThemeService } from '../../services/theme.service'; // Service pour gérer les thèmes
import { Theme } from '../../models/theme'; // Modèle de données pour les thèmes
import { AuthService } from '../../services/auth.service'; // Service d'authentification pour obtenir l'utilisateur actuel
import { UserService } from '../../services/user.service'; // Service pour gérer les utilisateurs
import { User } from '../../models/User'; // Modèle de données pour les utilisateurs
import { Subscription } from 'rxjs'; // Importer Subscription

@Component({
    selector: 'app-theme-list',
    templateUrl: './theme-list.component.html',
    styleUrls: ['./theme-list.component.scss']
})
export class ThemeListComponent implements OnInit, OnDestroy { // Implémenter OnDestroy
    themes: Theme[] = []; // Liste des thèmes disponibles
    subscribedThemes: Theme[] = []; // Liste des thèmes auxquels l'utilisateur est abonné
    errorMessage: string | null = null; // Message d'erreur en cas de problème

    private subscriptions: Subscription = new Subscription(); // Déclarer une propriété pour stocker les abonnements

    // Le constructeur injecte les services nécessaires : themeService, authService, et userService
    constructor(
        private themeService: ThemeService,
        private authService: AuthService,
        private userService: UserService
    ) { }

    // Méthode appelée au démarrage du composant
    ngOnInit(): void {
        this.loadThemes(); // Charger la liste des thèmes
        this.loadSubscribedThemes(); // Charger les thèmes auxquels l'utilisateur est abonné
    }

    // Méthode appelée lors de la destruction du composant
    ngOnDestroy(): void {
        this.subscriptions.unsubscribe(); // Se désabonner de tous les abonnements
    }

    // Charger tous les thèmes disponibles
    loadThemes(): void {
        const themesSubscription = this.themeService.getThemes().subscribe(
            themes => this.themes = themes, // En cas de succès, mettre à jour la liste des thèmes
            error => this.errorMessage = 'Erreur lors du chargement des thèmes.' // En cas d'erreur, afficher un message d'erreur
        );
        this.subscriptions.add(themesSubscription); // Ajouter l'abonnement à la liste
    }

    // Charger les thèmes auxquels l'utilisateur est abonné
    loadSubscribedThemes(): void {
        const userId = this.authService.getUserId(); // Obtenir l'ID de l'utilisateur via le service d'authentification
        if (userId) {
            const subscribedThemesSubscription = this.userService.getSubscribedThemes(userId).subscribe(
                (user: User) => {
                    this.subscribedThemes = user.subscribedThemes; // Mettre à jour la liste des thèmes abonnés
                },
                error => {
                    this.errorMessage = 'Erreur lors du chargement des thèmes abonnés.'; // Afficher un message d'erreur en cas d'échec
                }
            );
            this.subscriptions.add(subscribedThemesSubscription); // Ajouter l'abonnement à la liste
        }
    }

    // Vérifier si un thème est déjà abonné par l'utilisateur
    isSubscribed(themeId: number): boolean {
        return this.subscribedThemes.some(theme => theme.id === themeId); // Retourne true si l'utilisateur est abonné au thème
    }

    // S'abonner à un thème
    subscribeToTheme(themeId: number): void {
        const userId = this.authService.getUserId(); // Obtenir l'ID de l'utilisateur
        if (userId) {
            const subscribeSubscription = this.userService.subscribeToTheme(userId, themeId).subscribe(
                (user: User) => {
                    this.subscribedThemes = user.subscribedThemes; // Mettre à jour les thèmes abonnés après l'abonnement
                },
                error => {
                    this.errorMessage = 'Erreur lors de l\'abonnement au thème.'; // Afficher un message en cas d'erreur
                }
            );
            this.subscriptions.add(subscribeSubscription); // Ajouter l'abonnement à la liste
        } else {
            this.errorMessage = 'Utilisateur non authentifié.'; // Afficher un message si l'utilisateur n'est pas authentifié
        }
    }

    // Se désabonner d'un thème
    unsubscribeFromTheme(themeId: number): void {
        const userId = this.authService.getUserId(); // Obtenir l'ID de l'utilisateur
        if (userId) {
            const unsubscribeSubscription = this.userService.unsubscribeFromTheme(userId, themeId).subscribe(
                (user: User) => {
                    this.subscribedThemes = user.subscribedThemes; // Mettre à jour les thèmes abonnés après le désabonnement
                },
                error => {
                    this.errorMessage = 'Erreur lors du désabonnement du thème.'; // Afficher un message en cas d'erreur
                }
            );
            this.subscriptions.add(unsubscribeSubscription); // Ajouter l'abonnement à la liste
        } else {
            this.errorMessage = 'Utilisateur non authentifié.'; // Afficher un message si l'utilisateur n'est pas authentifié
        }
    }
}