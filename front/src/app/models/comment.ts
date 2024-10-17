export interface Comment {
    id: number; // Identifiant unique du commentaire
    username: string; // Nom d'utilisateur de l'auteur du commentaire
    content: string; // Contenu du commentaire
    createdAt: Date; // Date de création du commentaire
    authorId: number; // ID de l'auteur du commentaire
}