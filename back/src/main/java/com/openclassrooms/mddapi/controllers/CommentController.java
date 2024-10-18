package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.CommentDTO;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Méthode pour convertir une entité Comment en DTO
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

    // Méthode pour convertir un DTO en entité Comment
    private Comment convertToEntity(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(commentDTO.getId());
        comment.setUsername(commentDTO.getUsername());
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(commentDTO.getCreatedAt());
        comment.setAuthorId(commentDTO.getAuthorId());
        return comment;
    }

    // Récupérer tous les commentaires pour un article donné
    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticle(articleId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne 204 si pas de contenu
        }
        List<CommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDTOs);
    }

    // Ajouter un commentaire à un article donné
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentDTO> addComment(@PathVariable Long articleId, @RequestBody CommentDTO commentDTO) {
        Comment comment = convertToEntity(commentDTO);
        Comment savedComment = commentService.addComment(articleId, comment);
        return ResponseEntity.ok(convertToDTO(savedComment));
    }
}
