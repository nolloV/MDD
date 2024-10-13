package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // Récupérer les commentaires par article et les convertir en DTO
    public List<CommentDTO> getCommentsByArticle(Long articleId) {
        List<Comment> comments = commentRepository.findByArticleId(articleId);
        return comments.stream()
                .map(this::convertToDTO) // Convertir chaque entité en DTO
                .collect(Collectors.toList());
    }

    // Ajouter un commentaire à un article et convertir le DTO en entité
    public CommentDTO addComment(Long articleId, CommentDTO commentDTO) {
        // Trouver l'article associé
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        Comment comment = convertToEntity(commentDTO);
        comment.setArticle(article);
        comment.setCreatedAt(new Date());

        Comment savedComment = commentRepository.save(comment);
        return convertToDTO(savedComment);
    }

    // Conversion Entité -> DTO
    private CommentDTO convertToDTO(Comment comment) {
        return new CommentDTO(
                comment.getId(),
                comment.getUsername(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getArticle().getId() // Récupérer l'ID de l'article
        );
    }

    // Conversion DTO -> Entité
    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setUsername(commentDTO.getUsername());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        return comment;
    }
}
