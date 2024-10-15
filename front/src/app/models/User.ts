import { Theme } from "./theme";

export interface User {
    id: number;
    username: string;
    email: string;
    password?: string;
    subscribedThemes: string[]; // Ajout de la propriété subscribedThemes
}