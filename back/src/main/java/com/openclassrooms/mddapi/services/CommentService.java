package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentDTO;
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
import java.util.stream.Collectors;

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
     * Récupérer les commentaires par article et les convertir en DTO.
     *
     * @param articleId l'ID de l'article
     * @return une liste de CommentDTO
     */
    public List<CommentDTO> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(this::convertToDTO) // Convertir chaque entité en DTO
                .collect(Collectors.toList());
    }

    /**
     * Ajouter un commentaire à un article et convertir le DTO en entité.
     *
     * @param articleId l'ID de l'article
     * @param commentDTO les informations du commentaire à ajouter
     * @return le CommentDTO du commentaire ajouté
     */
    public CommentDTO addComment(Long articleId, CommentDTO commentDTO) {
        // Trouver l'article associé
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        Comment comment = convertToEntity(commentDTO);
        comment.setArticle(article);
        comment.setCreatedAt(new Date());

        // Récupérer le nom d'utilisateur à partir de l'ID de l'auteur
        String authorUsername = userRepository.findById(commentDTO.getAuthorId())
                .map(user -> user.getUsername())
                .orElse("Unknown");
        comment.setUsername(authorUsername);

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    /**
     * Conversion Entité -> DTO.
     *
     * @param comment l'entité Comment
     * @return le CommentDTO correspondant
     */
    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getArticle().getId(), // Récupérer l'ID de l'article
                comment.getAuthorId() // Récupérer l'ID de l'auteur
        );
    }

    /**
     * Conversion DTO -> Entité.
     *
     * @param commentDTO le CommentDTO
     * @return l'entité Comment correspondante
     */
    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setUsername(commentDTO.getUsername());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setAuthorId(commentDTO.getAuthorId());
        return comment;
    }
}
