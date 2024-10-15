export interface User {
    id: number;
    username: string;
    email: string;
    themes: string[]; // Liste des titres des thèmes auxquels l'utilisateur est abonné
}