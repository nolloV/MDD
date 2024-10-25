import { Component, OnInit, OnDestroy } from '@angular/core'; // Importer OnDestroy
import { ActivatedRoute } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { ThemeService } from 'src/app/services/theme.service';
import { Article } from 'src/app/models/article';
import { Theme } from 'src/app/models/theme';
import { Comment } from 'src/app/models/comment';
import { faPaperPlane, faArrowLeft } from '@fortawesome/free-solid-svg-icons'; // Importer les icônes
import { Location } from '@angular/common'; // Pour retourner à la page précédente
import { AuthService } from 'src/app/services/auth.service'; // Importer le service d'authentification
import { Subscription } from 'rxjs'; // Importer Subscription

@Component({
    selector: 'app-article-detail',
    templateUrl: './article-detail.component.html',
    styleUrls: ['./article-detail.component.scss']
})
export class ArticleDetailComponent implements OnInit, OnDestroy { // Implémenter OnDestroy
    article: Article | null = null; // Stocker l'article à afficher
    theme: Theme | null = null; // Stocker le thème de l'article
    comments: Comment[] = []; // Stocker les commentaires de l'article
    newComment: Comment = { id: 0, username: '', content: '', createdAt: new Date(), authorId: 0 }; // Initialiser un nouveau commentaire
    faPaperPlane = faPaperPlane; // Déclarer l'icône de l'avion en papier
    faArrowLeft = faArrowLeft;   // Déclarer l'icône de la flèche
    commentSuccessMessage: string | null = null; // Message de confirmation pour l'ajout de commentaire
    commentErrorMessage: string | null = null;   // Message d'erreur pour l'ajout de commentaire

    private subscriptions: Subscription = new Subscription(); // Déclarer une propriété pour stocker les abonnements

    constructor(
        private route: ActivatedRoute, // Injection du service ActivatedRoute pour accéder aux paramètres de la route
        private articleService: ArticleService, // Injection du service ArticleService pour les opérations sur les articles
        private themeService: ThemeService, // Injection du service ThemeService pour les opérations sur les thèmes
        private location: Location, // Injection du service Location pour retourner à la page précédente
        private authService: AuthService // Injection du service AuthService pour l'authentification
    ) { }

    // Méthode appelée lors de l'initialisation du composant
    ngOnInit(): void {
        this.loadArticle(); // Charger l'article et ses commentaires
    }

    // Méthode appelée lors de la destruction du composant
    ngOnDestroy(): void {
        this.subscriptions.unsubscribe(); // Se désabonner de tous les abonnements
    }

    // Charger l'article et ses commentaires
    loadArticle(): void {
        const articleId = +this.route.snapshot.paramMap.get('id')!; // Récupérer l'ID de l'article à partir des paramètres de la route
        if (articleId) {
            const articleSubscription = this.articleService.getArticleById(articleId).subscribe(article => {
                this.article = article; // Stocker l'article récupéré
                this.loadTheme(article.themeId); // Charger le thème de l'article
                // Charger les commentaires de l'article
                const commentsSubscription = this.articleService.getCommentsByArticleId(articleId).subscribe(comments => {
                    this.comments = comments || []; // Assurer que comments est un tableau vide s'il n'y a pas de commentaires
                });
                this.subscriptions.add(commentsSubscription); // Ajouter l'abonnement à la liste
            });
            this.subscriptions.add(articleSubscription); // Ajouter l'abonnement à la liste
        }
    }

    // Charger le thème de l'article
    loadTheme(themeId: number): void {
        const themeSubscription = this.themeService.getThemeById(themeId).subscribe(theme => {
            this.theme = theme; // Stocker le thème récupéré
        });
        this.subscriptions.add(themeSubscription); // Ajouter l'abonnement à la liste
    }

    // Ajouter un commentaire à l'article
    addComment(): void {
        this.commentSuccessMessage = null;  // Réinitialiser le message de succès
        this.commentErrorMessage = null;    // Réinitialiser le message d'erreur

        // Utiliser le nom d'utilisateur
        const username = this.authService.getUsername();
        const userId = this.authService.getUserId(); // Assurez-vous que cette méthode existe dans AuthService
        if (username && userId) {
            this.newComment.username = username; // Assigner le nom d'utilisateur au commentaire
            this.newComment.authorId = userId; // Assigner l'ID de l'utilisateur au commentaire
        } else {
            this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. Utilisateur non authentifié.';
            return;
        }

        // Vérifier si l'article est bien défini et si le contenu du commentaire n'est pas vide
        if (this.article && this.newComment.content) {
            const commentSubscription = this.articleService.addCommentToArticle(this.article.id, this.newComment)
                .subscribe(
                    (comment: Comment) => {
                        if (!this.comments) {
                            this.comments = []; // S'assurer que comments est un tableau s'il est null
                        }
                        this.comments.push(comment); // Ajouter le nouveau commentaire à la liste
                        this.newComment = { id: 0, username: '', content: '', createdAt: new Date(), authorId: 0 }; // Réinitialiser le formulaire
                        this.commentSuccessMessage = 'Votre commentaire a été ajouté avec succès.'; // Afficher un message de succès
                    },
                    (error) => {
                        console.error('Erreur lors de la soumission du commentaire :', error);
                        this.commentErrorMessage = 'Erreur lors de l\'ajout du commentaire. Veuillez réessayer.'; // Afficher un message d'erreur
                    }
                );
            this.subscriptions.add(commentSubscription); // Ajouter l'abonnement à la liste
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