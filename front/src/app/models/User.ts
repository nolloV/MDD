import { Theme } from "./theme";

export interface User {
    id: number;
    username: string;
    email: string;
    password?: string;
    subscribedThemes: Theme[]; // Utilisation d'un tableau de Theme
}