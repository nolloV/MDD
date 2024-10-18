package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Service pour gérer les opérations liées aux commentaires.
 */
@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository; // Référentiel pour les commentaires

    @Autowired
    private ArticleRepository articleRepository; // Référentiel pour les articles

    @Autowired
    private UserRepository userRepository; // Référentiel pour les utilisateurs

    /**
     * Récupérer les commentaires par article.
     *
     * @param articleId l'ID de l'article
     * @return une liste de Comment
     */
    public List<Comment> getCommentsByArticle(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    /**
     * Ajouter un commentaire à un article.
     *
     * @param articleId l'ID de l'article
     * @param comment les informations du commentaire à ajouter
     * @return le Comment ajouté
     */
    public Comment addComment(Long articleId, Comment comment) {
        // Trouver l'article associé
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        comment.setArticle(article);
        comment.setCreatedAt(new Date());

        // Récupérer le nom d'utilisateur à partir de l'ID de l'auteur
        String authorUsername = userRepository.findById(comment.getAuthorId())
                .map(user -> user.getUsername())
                .orElse("Unknown");
        comment.setUsername(authorUsername);

        return commentRepository.save(comment);
    }
}
