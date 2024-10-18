package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ThemeRepository themeRepository;

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Article getArticleById(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    public Article createArticle(Article article) {
        if (article.getTheme() == null || article.getTheme().getId() == null) {
            throw new ResourceNotFoundException("Theme must be provided for the article.");
        }

        Theme theme = themeRepository.findById(article.getTheme().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + article.getTheme().getId()));

        article.setTheme(theme);
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article updatedArticle) {
        return articleRepository.findById(id).map(article -> {
            article.setTitle(updatedArticle.getTitle());
            article.setContent(updatedArticle.getContent());
            article.setAuthor(updatedArticle.getAuthor());
            article.setAuthorId(updatedArticle.getAuthorId());
            Theme theme = themeRepository.findById(updatedArticle.getTheme().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + updatedArticle.getTheme().getId()));
            article.setTheme(theme);
            return articleRepository.save(article);
        }).orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
    }

    public boolean deleteArticle(Long id) {
        return articleRepository.findById(id).map(article -> {
            articleRepository.delete(article);
            return true;
        }).orElse(false);
    }

    public List<Article> getArticlesByThemeId(Long themeId) {
        return articleRepository.findByThemeId(themeId);
    }
}
