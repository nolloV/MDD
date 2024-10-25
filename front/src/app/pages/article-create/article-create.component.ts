import { Component, OnInit, OnDestroy } from '@angular/core'; // Importer OnDestroy
import { Router } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { ThemeService } from 'src/app/services/theme.service';
import { Article } from 'src/app/models/article';
import { Theme } from 'src/app/models/theme';
import { AuthService } from 'src/app/services/auth.service';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'; // Importer l'icône de la flèche
import { Subscription } from 'rxjs'; // Importer Subscription

@Component({
    selector: 'app-article-create',
    templateUrl: './article-create.component.html',
    styleUrls: ['./article-create.component.scss']
})
export class ArticleCreateComponent implements OnInit, OnDestroy { // Implémenter OnDestroy
    // Initialisation de l'article avec des valeurs par défaut
    article: Article = { id: 0, title: '', content: '', themeId: 0, theme: '', authorId: 0, author: '', createdAt: new Date(), comments: [] };
    themes: Theme[] = []; // Tableau pour stocker les thèmes disponibles
    errorMessage: string | null = null; // Message d'erreur à afficher en cas de problème

    faArrowLeft = faArrowLeft; // Déclarer l'icône de la flèche

    private subscriptions: Subscription = new Subscription(); // Déclarer une propriété pour stocker les abonnements

    constructor(
        private articleService: ArticleService, // Injecter ArticleService
        private themeService: ThemeService, // Injecter ThemeService
        private authService: AuthService, // Injecter AuthService
        private router: Router // Injecter Router
    ) { }

    // Méthode appelée lors de l'initialisation du composant
    ngOnInit(): void {
        this.loadThemes(); // Charger les thèmes disponibles
    }

    // Méthode appelée lors de la destruction du composant
    ngOnDestroy(): void {
        this.subscriptions.unsubscribe(); // Se désabonner de tous les abonnements
    }

    // Charger les thèmes disponibles
    loadThemes(): void {
        const themeSubscription = this.themeService.getThemes().subscribe(
            (themes: Theme[]) => {
                this.themes = themes; // Stocker les thèmes dans le tableau
            },
            (error) => {
                console.error('Erreur lors du chargement des thèmes:', error);
                this.errorMessage = 'Erreur lors du chargement des thèmes.'; // Afficher un message d'erreur
            }
        );
        this.subscriptions.add(themeSubscription); // Ajouter l'abonnement à la liste
    }

    // Créer un article
    createArticle(): void {
        const currentUser = this.authService.getUsername(); // Récupérer le nom d'utilisateur à partir du JWT
        const userId = this.authService.getUserId(); // Récupérer l'ID de l'utilisateur

        if (currentUser && userId !== null) {
            this.article.author = currentUser; // Assigner le nom d'utilisateur à author
            this.article.authorId = userId; // Assigner l'ID de l'utilisateur à authorId
        } else {
            this.errorMessage = 'Utilisateur non authentifié.'; // Afficher un message d'erreur si l'utilisateur n'est pas authentifié
            return;
        }

        // Vérifier que tous les champs requis sont remplis
        if (this.article.title && this.article.content && this.article.themeId) {
            const articleSubscription = this.articleService.addArticle(this.article).subscribe(
                (newArticle: Article) => {
                    this.router.navigate(['/articles']); // Rediriger vers la liste des articles après la création
                },
                (error) => {
                    console.error('Erreur lors de la création de l\'article:', error);
                    this.errorMessage = 'Erreur lors de la création de l\'article. Veuillez réessayer.'; // Afficher un message d'erreur en cas de problème
                }
            );
            this.subscriptions.add(articleSubscription); // Ajouter l'abonnement à la liste
        } else {
            this.errorMessage = 'Veuillez remplir tous les champs.'; // Afficher un message d'erreur si des champs sont manquants
        }
    }

    // Retour en arrière
    goBack(): void {
        this.router.navigate(['/articles']); // Rediriger vers la liste des articles
    }
}