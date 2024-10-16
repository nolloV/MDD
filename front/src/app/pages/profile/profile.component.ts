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
    subscriptions: Theme[] = []; // Tableau pour stocker les thèmes auxquels l'utilisateur est abonné
    errorMessage: string | null = null; // Message d'erreur à afficher en cas de problème
    saveSuccess: boolean = false; // Variable pour indiquer si la sauvegarde a été effectuée avec succès

    // Constructeur pour injecter les services nécessaires
    constructor(
        private router: Router,
        private authService: AuthService,
        private userService: UserService
    ) { }

    // Méthode appelée lors de l'initialisation du composant
    ngOnInit(): void {
        this.loadUserData(); // Charger les données de l'utilisateur
    }

    // Charger les données de l'utilisateur
    loadUserData(): void {
        const userId = this.authService.getUserId(); // Récupérer l'ID de l'utilisateur à partir du service d'authentification
        if (userId) {
            this.userService.getSubscribedThemes(userId).subscribe(
                (user: User) => {
                    this.user = user; // Mettre à jour les informations de l'utilisateur
                    this.subscriptions = user.subscribedThemes; // Mettre à jour les abonnements de l'utilisateur
                },
                error => {
                    this.errorMessage = 'Erreur lors de la récupération des informations utilisateur.'; // Afficher un message d'erreur en cas de problème
                }
            );
        } else {
            this.errorMessage = 'Utilisateur non authentifié.'; // Afficher un message d'erreur si l'utilisateur n'est pas authentifié
        }
    }

    // Sauvegarder les modifications apportées aux informations de l'utilisateur
    saveChanges(): void {
        if (this.user) {
            this.authService.updateUser(this.user).subscribe(
                response => {
                    this.saveSuccess = true; // Indiquer que la sauvegarde a été effectuée avec succès
                    alert('Informations sauvegardées avec succès. Veuillez vous reconnecter.');
                    this.logout(); // Déconnecter l'utilisateur pour qu'il puisse se reconnecter avec le nouveau token
                },
                error => {
                    this.errorMessage = 'Erreur lors de la sauvegarde des informations.'; // Afficher un message d'erreur en cas de problème
                }
            );
        }
    }

    // Déconnecter l'utilisateur
    logout(): void {
        this.authService.logout(); // Appeler la méthode de déconnexion du service d'authentification
        this.router.navigate(['']); // Rediriger vers la page d'accueil
    }

    // Désabonner l'utilisateur d'un thème
    unsubscribe(themeId: number): void {
        const userId = this.authService.getUserId(); // Récupérer l'ID de l'utilisateur à partir du service d'authentification
        if (userId) {
            this.userService.unsubscribeFromTheme(userId, themeId).subscribe(
                (user: User) => {
                    this.subscriptions = user.subscribedThemes; // Mettre à jour les abonnements de l'utilisateur
                    // Désabonnement réussi
                },
                error => {
                    this.errorMessage = 'Erreur lors du désabonnement du thème.'; // Afficher un message d'erreur en cas de problème
                }
            );
        }
    }
}