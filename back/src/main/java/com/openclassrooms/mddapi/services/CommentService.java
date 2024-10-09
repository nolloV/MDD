package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Comment;
import com.openclassrooms.mddapi.repositories.ArticleRepository;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException; // Assure-toi d'importer l'exception
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // Récupérer les commentaires par article
    public List<Comment> getCommentsByArticle(Long articleId) {
        return commentRepository.findByArticleId(articleId);
    }

    // Ajouter un commentaire à un article
    public Comment addComment(Long articleId, Comment comment) {
        // Trouver l'article associé
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new ResourceNotFoundException("Article not found with id " + articleId));

        comment.setArticle(article);
        comment.setCreatedAt(new Date()); // Ajout de la date de création
        return commentRepository.save(comment);
    }
}
