package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Récupérer tous les articles
    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    // Récupérer un article par ID
    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    // Créer un nouvel article
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    // Mettre à jour un article existant
    public Article updateArticle(Long id, Article updatedArticle) {
        Article article = getArticleById(id); // Utilise la méthode de récupération d'article
        article.setTitle(updatedArticle.getTitle());
        article.setContent(updatedArticle.getContent());
        return articleRepository.save(article);
    }

    // Supprimer un article
    public void deleteArticle(Long id) {
        Article article = getArticleById(id); // Utilise la méthode de récupération d'article
        articleRepository.delete(article);
    }
}
