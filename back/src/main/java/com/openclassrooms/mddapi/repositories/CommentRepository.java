package com.openclassrooms.mddapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.entities.Comment;

// Interface de repository pour l'entité Comment
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // Méthode pour trouver les commentaires par ID d'article
    List<Comment> findByArticleId(Long articleId);
}
