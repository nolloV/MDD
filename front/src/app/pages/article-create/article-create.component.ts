import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ArticleService } from 'src/app/services/article.service';
import { ThemeService } from 'src/app/services/theme.service';
import { Article } from 'src/app/models/article';
import { Theme } from 'src/app/models/theme';
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'; // Importer l'icône de la flèche

@Component({
    selector: 'app-article-create',
    templateUrl: './article-create.component.html',
    styleUrls: ['./article-create.component.scss']
})
export class ArticleCreateComponent implements OnInit {
    article: Article = { id: 0, title: '', content: '', theme: '', author: '', createdAt: new Date() };
    themes: Theme[] = [];
    errorMessage: string | null = null;

    // Utilisateur simulé
    fakeUser = {
        username: 'FakeUser123',
        role: 'admin'
    };

    faArrowLeft = faArrowLeft; // Déclarer l'icône de la flèche

    constructor(
        private articleService: ArticleService,
        private themeService: ThemeService,
        private router: Router
    ) { }

    ngOnInit(): void {
        this.loadThemes();
    }

    // Charger les thèmes
    loadThemes(): void {
        this.themeService.getThemes().subscribe(
            (themes: Theme[]) => {
                this.themes = themes;
            },
            (error) => {
                console.error('Erreur lors du chargement des thèmes:', error);
                this.errorMessage = 'Erreur lors du chargement des thèmes.';
            }
        );
    }

    // Créer un article
    createArticle(): void {
        this.article.author = this.fakeUser.username;

        if (this.article.title && this.article.content && this.article.theme) {
            this.articleService.addArticle(this.article).subscribe(
                (newArticle: Article) => {
                    this.router.navigate(['/articles']);
                },
                (error) => {
                    console.error('Erreur lors de la création de l\'article:', error);
                    this.errorMessage = 'Erreur lors de la création de l\'article. Veuillez réessayer.';
                }
            );
        } else {
            this.errorMessage = 'Veuillez remplir tous les champs.';
        }
    }

    // Retour en arrière
    goBack(): void {
        this.router.navigate(['/articles']);
    }
}
