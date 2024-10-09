package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    // Récupérer tous les articles
    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    // Récupérer un article par ID
    @GetMapping("/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(article);
    }

    // Créer un nouvel article
    @PostMapping
    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
        Article newArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    // Mettre à jour un article existant
    @PutMapping("/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        Article savedArticle = articleService.updateArticle(id, updatedArticle);
        return ResponseEntity.ok(savedArticle);
    }

    // Supprimer un article
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return ResponseEntity.ok().build();
    }
}
