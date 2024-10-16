import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/models/article';
import { Comment } from 'src/app/models/comment';
import { faPaperPlane, faArrowLeft } from '@fortawesome/free-solid-svg-icons'; // Importer les icônes
import { Location } from '@angular/common'; // Pour retourner à la page précédente
import { AuthService } from 'src/app/services/auth.service'; // Importer le service d'authentification

@Component({
    selector: 'app-article-detail',
    templateUrl: './article-detail.component.html',
    styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
    article: Article | null = null; // Stocker l'article à afficher
    comments: Comment[] = []; // Stocker les commentaires de l'article
    newComment: Comment = { id: 0, username: '', content: '', createdAt: new Date() }; // Initialiser un nouveau commentaire
    faPaperPlane = faPaperPlane; // Déclarer l'icône de l'avion en papier
    faArrowLeft = faArrowLeft;   // Déclarer l'icône de la flèche
    commentSuccessMessage: string | null = null; // Message de confirmation pour l'ajout de commentaire
    commentErrorMessage: string | null = null;   // Message d'erreur pour l'ajout de commentaire

    constructor(
        private route: ActivatedRoute, // Injection du service ActivatedRoute pour accéder aux paramètres de la route
        private articleService: ArticleService, // Injection du service ArticleService pour les opérations sur les articles
        private location: Location, // Injection du service Location pour retourner à la page précédente
        private authService: AuthService // Injection du service AuthService pour l'authentification
    ) { }

    // Méthode appelée lors de l'initialisation du composant
    ngOnInit(): void {
        this.loadArticle(); // Charger l'article et ses commentaires
    }

    // Charger l'article et ses commentaires
    loadArticle(): void {
        const articleId = +this.route.snapshot.paramMap.get('id')!; // Récupérer l'ID de l'article à partir des paramètres de la route
        if (articleId) {
            this.articleService.getArticleById(articleId).subscribe(article => {
                this.article = article; // Stocker l'article récupéré
                // Charger les commentaires de l'article
                this.articleService.getCommentsByArticleId(articleId).subscribe(comments => {
                    this.comments = comments || []; // Assurer que comments est un tableau vide s'il n'y a pas de commentaires
                });
            });
        }
    }

    // Ajouter un commentaire à l'article
    addComment(): void {
        this.commentSuccessMessage = null;  // Réinitialiser le message de succès
        this.commentErrorMessage = null;    // Réinitialiser le message d'erreur

        // Utiliser le nom d'utilisateur connecté
        const username = this.authService.getUsername();
        if (username) {
            this.newComment.username = username; // Assigner le nom d'utilisateur au commentaire
        } else {
            this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. Utilisateur non authentifié.';
            return;
        }

        // Vérifier si l'article est bien défini et si le contenu du commentaire n'est pas vide
        if (this.article && this.newComment.content) {
            this.articleService.addCommentToArticle(this.article.id, this.newComment)
                .subscribe(
                    (comment: Comment) => {
                        if (!this.comments) {
                            this.comments = []; // S'assurer que comments est un tableau s'il est null
                        }
                        this.comments.push(comment); // Ajouter le nouveau commentaire à la liste
                        this.newComment = { id: 0, username: '', content: '', createdAt: new Date() }; // Réinitialiser le formulaire
                        this.commentSuccessMessage = 'Votre commentaire a été ajouté avec succès.'; // Afficher un message de succès
                    },
                    (error) => {
                        console.error('Erreur lors de la soumission du commentaire :', error);
                        this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. Veuillez réessayer.'; // Afficher un message d'erreur
                    }
                );
        } else {
            console.error('L\'article n\'est pas encore chargé ou le commentaire est vide.');
            this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. L\'article n\'est pas encore disponible.'; // Afficher un message d'erreur
        }
    }

    // Méthode pour retourner en arrière
    goBack(): void {
        this.location.back(); // Utiliser le service Location pour retourner à la page précédente
    }
}