import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
    // Simule les données utilisateur
    user = {
        username: 'FakeUser123',
        email: 'fakeuser@example.com'
    };

    // Simule les abonnements de l'utilisateur
    subscriptions = [
        {
            theme: {
                id: 1,
                title: 'Programmation Java',
                description: 'Description: Apprenez tout sur Java, un langage orienté objet utilisé pour le développement backend.'
            }
        },
        {
            theme: {
                id: 2,
                title: 'Développement Frontend',
                description: 'Description: Explorez le développement frontend avec HTML, CSS, et JavaScript.'
            }
        }
    ];

    errorMessage: string | null = null;

    constructor() { }

    ngOnInit(): void {
        // Aucune action nécessaire pour l'instant, les données sont statiques
    }

    // Simule la sauvegarde des modifications de l'utilisateur
    saveChanges(): void {
        alert('Informations sauvegardées : ' + JSON.stringify(this.user));
    }

    // Simule la déconnexion
    logout(): void {
        alert('Déconnecté');
    }

    // Simule la désinscription d'un thème
    unsubscribe(themeId: number): void {
        this.subscriptions = this.subscriptions.filter(subscription => subscription.theme.id !== themeId);
        alert('Désinscrit du thème avec ID : ' + themeId);
    }
}
