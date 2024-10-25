import { Component, OnInit, OnDestroy } from '@angular/core'; // Importer OnDestroy
import { Article } from 'src/app/models/article';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/services/auth.service';
import { faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons'; // Import des icônes pour tri
import { Router } from '@angular/router'; // Importer Router pour la navigation
import { Subscription } from 'rxjs'; // Importer Subscription

@Component({
    selector: 'app-article-list',
    templateUrl: './article-list.component.html',
    styleUrls: ['./article-list.component.scss'],
})
export class ArticleListComponent implements OnInit, OnDestroy { // Implémenter OnDestroy
    articles: Article[] = []; // Tableau pour stocker les articles
    sortedBy: string = 'date_desc'; // Par défaut, trié par date décroissante (du plus récent au plus ancien)
    faArrowDown = faArrowDown; // Icône pour le tri descendant
    faArrowUp = faArrowUp;     // Icône pour le tri ascendant
    isDesc: boolean = true;    // Indique si le tri est descendant ou ascendant

    private subscriptions: Subscription = new Subscription(); // Déclarer une propriété pour stocker les abonnements

    constructor(
        private userService: UserService,
        private authService: AuthService,
        private router: Router
    ) { } // Injecter les services nécessaires

    // Méthode appelée lors de l'initialisation du composant
    ngOnInit(): void {
        this.loadArticles(); // Charger les articles
    }

    // Méthode appelée lors de la destruction du composant
    ngOnDestroy(): void {
        this.subscriptions.unsubscribe(); // Se désabonner de tous les abonnements
    }

    // Charger les articles des thèmes abonnés et s'assurer que created_at est toujours une date valide
    loadArticles(): void {
        const userId = this.authService.getUserId(); // Récupérer l'ID de l'utilisateur
        if (userId) {
            const articlesSubscription = this.userService.getArticlesForSubscribedThemes(userId).subscribe((articles) => {
                this.articles = articles.map((article) => ({
                    ...article,
                    createdAt: article.createdAt ? new Date(article.createdAt) : new Date(), // Conversion explicite de `created_at`
                }));
                this.sortArticles(); // Tri après chargement et conversion des dates
            });
            this.subscriptions.add(articlesSubscription); // Ajouter l'abonnement à la liste
        }
    }

    // Fonction pour trier les articles en fonction de `created_at`
    sortArticles(): void {
        this.articles.sort((a, b) => {
            const dateA = a.createdAt.getTime();
            const dateB = b.createdAt.getTime();
            return this.isDesc ? dateB - dateA : dateA - dateB; // Tri descendant ou ascendant
        });
    }

    // Fonction appelée lors du clic sur l'icône de tri pour changer l'ordre
    toggleSortOrder(): void {
        this.isDesc = !this.isDesc; // Inverser l'ordre de tri
        this.sortArticles(); // Réappliquer le tri après inversion
    }

    // Méthode pour rediriger vers les détails de l'article
    viewArticle(articleId: number): void {
        this.router.navigate(['/article', articleId]); // Naviguer vers la page de détails de l'article
    }
}