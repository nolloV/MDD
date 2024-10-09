import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { Article } from 'src/app/models/article';
import { Comment } from 'src/app/models/comment';
import { faPaperPlane, faArrowLeft } from '@fortawesome/free-solid-svg-icons'; // Importer les icônes
import { Location } from '@angular/common'; // Pour retourner à la page précédente

@Component({
    selector: 'app-article-detail',
    templateUrl: './article-detail.component.html',
    styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit {
    article: Article | null = null;
    comments: Comment[] = [];
    newComment: Comment = { id: 0, username: '', content: '', createdAt: new Date() };
    faPaperPlane = faPaperPlane; // Déclarer l'icône ici
    faArrowLeft = faArrowLeft;   // Déclarer l'icône de la flèche
    commentSuccessMessage: string | null = null; // Message de confirmation
    commentErrorMessage: string | null = null;   // Message d'erreur
    fakeUsername: string = 'TestUser';  // Utilisateur fictif

    constructor(
        private route: ActivatedRoute,
        private articleService: ArticleService,
        private location: Location // Injection du service Location
    ) { }

    ngOnInit(): void {
        this.loadArticle();
    }

    // Charger l'article et ses commentaires
    loadArticle(): void {
        const articleId = +this.route.snapshot.paramMap.get('id')!;
        if (articleId) {
            this.articleService.getArticleById(articleId).subscribe(article => {
                this.article = article;
                // Charger les commentaires de l'article
                this.articleService.getCommentsByArticleId(articleId).subscribe(comments => {
                    this.comments = comments || []; // Assure que comments est un tableau vide s'il n'y a pas de commentaires
                });
            });
        }
    }

    // Ajouter un commentaire à l'article
    addComment(): void {
        this.commentSuccessMessage = null;  // Réinitialise le message de succès
        this.commentErrorMessage = null;    // Réinitialise le message d'erreur

        // Utiliser l'utilisateur fictif
        this.newComment.username = this.fakeUsername;

        // Vérifie si l'article est bien défini
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
                        this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. Veuillez réessayer.';
                    }
                );
        } else {
            console.error('L\'article n\'est pas encore chargé ou le commentaire est vide.');
            this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. L\'article n\'est pas encore disponible.';
        }
    }

    // Méthode pour retourner en arrière
    goBack(): void {
        this.location.back();
    }
}
