package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
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
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    // Récupérer un article par son ID
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        return articleDTO != null ? ResponseEntity.ok(articleDTO) : ResponseEntity.notFound().build();
    }

    // Créer un nouvel article
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO newArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    // Mettre à jour un article existant
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO updatedArticleDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, updatedArticleDTO);
        return updatedArticle != null ? ResponseEntity.ok(updatedArticle) : ResponseEntity.notFound().build();
    }

    // Supprimer un article
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        boolean deleted = articleService.deleteArticle(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
