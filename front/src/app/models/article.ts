import { Comment } from "./comment";

export interface Article {
    id: number;
    title: string;
    content: string;
    author: string;
    createdAt: Date; // Correspond à la colonne `created_at` dans ta base de données
    theme: string; // Thème associé à l'article, propriété optionnelle
    comments?: Comment[]; // Tableau de commentaires, propriété optionnelle
}
