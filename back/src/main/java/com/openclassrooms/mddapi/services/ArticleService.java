package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service pour gérer les opérations liées aux articles.
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository; // Référentiel pour les opérations CRUD sur les articles

    @Autowired
    private UserRepository userRepository; // Référentiel pour les opérations CRUD sur les utilisateurs

    /**
     * Récupère tous les articles et les convertit en DTO.
     *
     * @return une liste de ArticleDTO
     */
    public List<ArticleDTO> getAllArticles() {
        return articleRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Récupère un article par son ID et le convertit en DTO.
     *
     * @param id l'ID de l'article à récupérer
     * @return l'article sous forme de ArticleDTO
     * @throws ResourceNotFoundException si l'article n'est pas trouvé
     */
    public ArticleDTO getArticleById(Long id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + id));
        return convertToDTO(article);
    }

    /**
     * Crée un nouvel article à partir d'un DTO et le sauvegarde.
     *
     * @param articleDTO les informations de l'article à créer
     * @return l'article créé sous forme de ArticleDTO
     */
    public ArticleDTO createArticle(ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        Article savedArticle = articleRepository.save(article);
        return convertToDTO(savedArticle);
    }

    /**
     * Met à jour un article existant à partir d'un DTO.
     *
     * @param id l'ID de l'article à mettre à jour
     * @param updatedArticleDTO les nouvelles informations de l'article
     * @return l'article mis à jour sous forme de ArticleDTO, ou null si
     * l'article n'est pas trouvé
     */
    public ArticleDTO updateArticle(Long id, ArticleDTO updatedArticleDTO) {
        return articleRepository.findById(id).map(article -> {
            article.setTitle(updatedArticleDTO.getTitle());
            article.setContent(updatedArticleDTO.getContent());
            article.setAuthor(updatedArticleDTO.getAuthor());
            article.setAuthorId(updatedArticleDTO.getAuthorId());
            Article savedArticle = articleRepository.save(article);
            return convertToDTO(savedArticle);
        }).orElse(null);
    }

    /**
     * Supprime un article par son ID.
     *
     * @param id l'ID de l'article à supprimer
     * @return true si l'article a été supprimé, false sinon
     */
    public boolean deleteArticle(Long id) {
        return articleRepository.findById(id).map(article -> {
            articleRepository.delete(article);
            return true;
        }).orElse(false);
    }

    /**
     * Convertit une entité Article en DTO.
     *
     * @param article l'entité Article à convertir
     * @return l'article sous forme de ArticleDTO
     */
    private ArticleDTO convertToDTO(Article article) {
        String authorUsername = userRepository.findById(article.getAuthorId())
                .map(user -> user.getUsername())
                .orElse("Unknown");

        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                authorUsername, // Utilisation de authorUsername ici
                article.getAuthorId(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getComments() != null
                ? article.getComments().stream().map(comment -> comment.getContent()).collect(Collectors.toList())
                : List.of()
        );
    }

    /**
     * Convertit un DTO en entité Article.
     *
     * @param articleDTO le DTO à convertir
     * @return l'entité Article
     */
    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setAuthor(articleDTO.getAuthor());
        article.setAuthorId(articleDTO.getAuthorId());
        return article;
    }
}
