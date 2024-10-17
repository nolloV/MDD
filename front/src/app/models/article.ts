// Définition de l'interface Article
export interface Article {
    id: number; // Identifiant unique de l'article
    title: string; // Titre de l'article
    content: string; // Contenu de l'article
    authorId: number; // ID de l'auteur de l'article
    author: string; // Nom d'utilisateur de l'auteur de l'article
    createdAt: Date; // Date de création de l'article, correspond à la colonne `created_at` dans la base de données
    themeId: number; // ID du thème associé à l'article
    theme?: string; // Nom du thème associé à l'article, propriété optionnelle
    comments?: Comment[]; // Tableau de commentaires associés à l'article, propriété optionnelle
}