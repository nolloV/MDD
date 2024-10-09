package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
