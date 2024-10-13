package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    // Récupérer tous les articles et les convertir en DTOs
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un article par ID et le convertir en DTO
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
        return convertToDTO(article);
    }

    // Créer un nouvel article à partir d'un DTO
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        Article savedArticle = articleRepository.save(article);
        return convertToDTO(savedArticle);
    }

    // Mettre à jour un article existant
    public ArticleDTO updateArticle(Long id, ArticleDTO updatedArticleDTO) {
        return articleRepository.findById(id).map(article -> {
            article.setTitle(updatedArticleDTO.getTitle());
            article.setContent(updatedArticleDTO.getContent());
            Article savedArticle = articleRepository.save(article);
            return convertToDTO(savedArticle);
        }).orElse(null);
    }

    // Supprimer un article
    public boolean deleteArticle(Long id) {
        return articleRepository.findById(id).map(article -> {
            articleRepository.delete(article);
            return true;
        }).orElse(false);
    }

    // Convertir un Article en ArticleDTO
    private ArticleDTO convertToDTO(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getComments().stream().map(comment -> comment.getContent()).collect(Collectors.toList())
        );
    }

    // Convertir un ArticleDTO en Article (entité)
    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setAuthor(articleDTO.getAuthor());
        return article;
    }
}
