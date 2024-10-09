package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Récupérer tous les commentaires pour un article donné
    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<Comment>> getCommentsByArticle(@PathVariable Long articleId) {
        List<Comment> comments = commentService.getCommentsByArticle(articleId);
        if (comments.isEmpty()) {
            return ResponseEntity.noContent().build(); // Retourne 204 si pas de contenu
        }
        return ResponseEntity.ok(comments);
    }

    // Ajouter un commentaire à un article donné
    @PostMapping("/{articleId}/comments")
    public ResponseEntity<Comment> addComment(@PathVariable Long articleId, @RequestBody Comment comment) {
        Comment savedComment = commentService.addComment(articleId, comment);
        return ResponseEntity.ok(savedComment);
    }
}
