import { Component, OnInit } from '@angular/core';
import { Article } from 'src/app/models/article';
import { ArticleService } from 'src/app/services/article.service';
import { faArrowDown, faArrowUp } from '@fortawesome/free-solid-svg-icons'; // Import des icônes pour tri
import { Router } from '@angular/router'; // Importer Router pour la navigation

@Component({
    selector: 'app-article-list',
    templateUrl: './article-list.component.html',
    styleUrls: ['./article-list.component.scss'],
})
export class ArticleListComponent implements OnInit {
    articles: Article[] = [];
    sortedBy: string = 'date_desc'; // Par défaut, trié par date décroissante (du plus récent au plus ancien)
    faArrowDown = faArrowDown; // Icône pour le tri descendant
    faArrowUp = faArrowUp;     // Icône pour le tri ascendant
    isDesc: boolean = true;    // Indique si le tri est descendant ou ascendant

    constructor(private articleService: ArticleService, private router: Router) { } // Injecter le Router

    ngOnInit(): void {
        this.loadArticles();
    }

    // Charger les articles et s'assurer que created_at est toujours une date valide
    loadArticles(): void {
        this.articleService.getArticles().subscribe((articles) => {
            this.articles = articles.map((article) => ({
                ...article,
                createdAt: article.createdAt ? new Date(article.createdAt) : new Date(), // Conversion explicite de `created_at`
            }));
            this.sortArticles(); // Tri après chargement et conversion des dates
        });
    }

    // Fonction pour trier les articles en fonction de `created_at`
    sortArticles(): void {
        this.articles.sort((a, b) => {
            const dateA = a.createdAt.getTime();
            const dateB = b.createdAt.getTime();
            return this.isDesc ? dateB - dateA : dateA - dateB;
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