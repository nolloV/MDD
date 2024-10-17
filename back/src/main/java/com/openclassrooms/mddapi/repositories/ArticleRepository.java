package com.openclassrooms.mddapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.openclassrooms.mddapi.entities.Article;
import java.util.List;
// Interface de repository pour l'entité Article

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByThemeId(Long themeId);
    // Hérite des méthodes CRUD de JpaRepository
}
