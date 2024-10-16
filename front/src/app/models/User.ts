import { Theme } from "./theme";

export interface User {
    id: number; // Identifiant unique de l'utilisateur
    username: string; // Nom d'utilisateur
    email: string; // Adresse email de l'utilisateur
    password?: string; // Mot de passe de l'utilisateur, propriété optionnelle
    subscribedThemes: Theme[]; // Tableau des thèmes auxquels l'utilisateur est abonné
}