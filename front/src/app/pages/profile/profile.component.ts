import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ValidationErrors } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/models/User';
import { Theme } from 'src/app/models/theme';
import { PasswordChangeRequest } from 'src/app/models/password-change-request.model'; // Import du modèle
import { passwordValidator } from 'src/app/validators/password.validator'; // Import du validateur

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
    passwordForm: FormGroup; // Formulaire pour le changement de mot de passe
    passwordErrorMessage: string | null = null; // Message d'erreur pour le changement de mot de passe
    passwordSuccessMessage: string | null = null; // Message de succès pour le changement de mot de passe

    // Constructeur pour injecter les services nécessaires
    constructor(
        private fb: FormBuilder,
        private router: Router,
        private authService: AuthService,
        private userService: UserService
    ) {
        // Initialiser le formulaire de changement de mot de passe avec les validateurs
        this.passwordForm = this.fb.group({
            currentPassword: ['', Validators.required],
            newPassword: ['', [Validators.required, passwordValidator]],
            confirmPassword: ['', Validators.required]
        }, { validator: this.passwordsMatchValidator });
    }

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

    // Valide que les mots de passe correspondent
    passwordsMatchValidator(group: FormGroup): ValidationErrors | null {
        const newPassword = group.get('newPassword')?.value;
        const confirmPassword = group.get('confirmPassword')?.value;
        return newPassword === confirmPassword ? null : { passwordsMismatch: true };
    }

    // Changer le mot de passe de l'utilisateur
    changePassword(): void {
        if (this.passwordForm.invalid) {
            this.passwordErrorMessage = 'Le mot de passe doit contenir au moins 8 caractères, une majuscule, une minuscule, un chiffre et un caractère spécial.';
            return;
        }

        const passwordChangeRequest: PasswordChangeRequest = {
            currentPassword: this.passwordForm.value.currentPassword,
            newPassword: this.passwordForm.value.newPassword
        };

        this.authService.changePassword(passwordChangeRequest).subscribe(
            response => {
                this.passwordSuccessMessage = 'Mot de passe changé avec succès.';
                this.passwordErrorMessage = null;
                this.passwordForm.reset(); // Réinitialiser les champs de mot de passe
            },
            error => {
                this.passwordErrorMessage = error.error ? error.error.error : 'Erreur lors du changement de mot de passe.';
                this.passwordSuccessMessage = null;
            }
        );
    }
}