package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour gérer les opérations liées aux articles.
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository; // Référentiel pour les opérations CRUD sur les articles

    @Autowired
    private ThemeRepository themeRepository; // Référentiel pour les opérations CRUD sur les thèmes

    /**
     * Récupère tous les articles.
     *
     * @return une liste de tous les articles.
     */
    public List<Article> getAllArticles() {
        // Appelle le référentiel pour récupérer tous les articles
        return articleRepository.findAll();
    }

    /**
     * Récupère un article par son ID.
     *
     * @param id l'ID de l'article à récupérer.
     * @return l'article correspondant.
     * @throws ResourceNotFoundException si l'article n'est pas trouvé.
     */
    public Article getArticleById(Long id) {
        // Appelle le référentiel pour récupérer l'article par son ID
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    /**
     * Crée un nouvel article.
     *
     * @param article les informations de l'article à créer.
     * @return l'article créé.
     * @throws ResourceNotFoundException si le thème associé n'est pas trouvé.
     */
    public Article createArticle(Article article) {
        // Vérifie si le thème associé existe
        if (article.getTheme() == null || article.getTheme().getId() == null) {
            throw new ResourceNotFoundException("Theme must be provided for the article.");
        }

        Theme theme = themeRepository.findById(article.getTheme().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + article.getTheme().getId()));

        // Associe le thème à l'article
        article.setTheme(theme);
        // Sauvegarde l'article dans le référentiel
        return articleRepository.save(article);
    }

    /**
     * Met à jour un article existant.
     *
     * @param id l'ID de l'article à mettre à jour.
     * @param updatedArticle les nouvelles informations de l'article.
     * @return l'article mis à jour.
     * @throws ResourceNotFoundException si l'article ou le thème associé n'est
     * pas trouvé.
     */
    public Article updateArticle(Long id, Article updatedArticle) {
        // Trouve l'article par son ID et le met à jour avec les nouvelles informations
        return articleRepository.findById(id).map(article -> {
            article.setTitle(updatedArticle.getTitle());
            article.setContent(updatedArticle.getContent());
            article.setAuthor(updatedArticle.getAuthor());
            article.setAuthorId(updatedArticle.getAuthorId());
            // Vérifie si le thème associé existe
            Theme theme = themeRepository.findById(updatedArticle.getTheme().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + updatedArticle.getTheme().getId()));
            // Associe le thème à l'article
            article.setTheme(theme);
            // Sauvegarde l'article mis à jour dans le référentiel
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    /**
     * Supprime un article par son ID.
     *
     * @param id l'ID de l'article à supprimer.
     * @return true si l'article a été supprimé, false sinon.
     */
    public boolean deleteArticle(Long id) {
        // Trouve l'article par son ID et le supprime
        return articleRepository.findById(id).map(article -> {
            articleRepository.delete(article);
            return true;
        }).orElse(false);
    }

    /**
     * Récupère les articles par ID de thème.
     *
     * @param themeId l'ID du thème.
     * @return une liste des articles associés au thème.
     */
    public List<Article> getArticlesByThemeId(Long themeId) {
        // Appelle le référentiel pour récupérer les articles par ID de thème
        return articleRepository.findByThemeId(themeId);
    }
}
