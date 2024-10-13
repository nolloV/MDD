import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { jwtDecode } from 'jwt-decode';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    user: any = {}; // Initialiser l'utilisateur avec un objet vide
    subscriptions: any[] = []; // Initialiser les abonnements avec un tableau vide
    errorMessage: string | null = null;

    constructor(private router: Router) { }

    ngOnInit(): void {
        this.loadUserData();
    }

    // Charger les données de l'utilisateur à partir du token JWT
    loadUserData(): void {
        const token = localStorage.getItem('token');
        if (token) {
            try {
                const decodedToken: any = jwtDecode(token);
                console.log('Decoded Token:', decodedToken); // Ajoutez ce log pour vérifier le contenu du token
                this.user = {
                    username: decodedToken.username,
                    email: decodedToken.email
                };
                console.log('User Data:', this.user); // Ajoutez ce log pour vérifier les données utilisateur
                // Charger les abonnements de l'utilisateur si disponibles dans le token
                if (decodedToken.subscriptions) {
                    this.subscriptions = decodedToken.subscriptions;
                    console.log('Subscriptions:', this.subscriptions); // Ajoutez ce log pour vérifier les abonnements
                }
            } catch (error) {
                this.errorMessage = 'Erreur lors du décodage du token';
                console.error(this.errorMessage, error); // Ajoutez ce log pour vérifier les erreurs
            }
        } else {
            this.errorMessage = 'Token non trouvé';
            console.error(this.errorMessage); // Ajoutez ce log pour vérifier les erreurs
        }
    }

    // Simule la sauvegarde des modifications de l'utilisateur
    saveChanges(): void {
        alert('Informations sauvegardées : ' + JSON.stringify(this.user));
    }

    // Gère la déconnexion
    logout(): void {
        // Supprime le token JWT du localStorage
        localStorage.removeItem('token');
        // Redirige vers la page de connexion
        this.router.navigate(['']);
        alert('Déconnecté');
    }

    // Simule la désinscription d'un thème
    unsubscribe(themeId: number): void {
        this.subscriptions = this.subscriptions.filter(subscription => subscription.theme.id !== themeId);
        alert('Désinscrit du thème avec ID : ' + themeId);
    }
}