package com.openclassrooms.mddapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.entities.Article;

// Interface de repository pour l'entité Article
public interface ArticleRepository extends JpaRepository<Article, Long> {
    // Hérite des méthodes CRUD de JpaRepository
}
