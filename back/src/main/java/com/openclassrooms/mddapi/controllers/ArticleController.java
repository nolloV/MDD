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

    /**
     * Récupère tous les articles.
     *
     * @return une liste de tous les articles sous forme de DTO.
     */
    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Récupère un article par son ID.
     *
     * @param id l'ID de l'article à récupérer.
     * @return l'article correspondant sous forme de DTO, ou une réponse 404 si
     * l'article n'est pas trouvé.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        ArticleDTO articleDTO = articleService.getArticleById(id);
        return articleDTO != null ? ResponseEntity.ok(articleDTO) : ResponseEntity.notFound().build();
    }

    /**
     * Crée un nouvel article.
     *
     * @param articleDTO les informations de l'article à créer.
     * @return l'article créé sous forme de DTO avec un statut 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        ArticleDTO newArticle = articleService.createArticle(articleDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newArticle);
    }

    /**
     * Met à jour un article existant.
     *
     * @param id l'ID de l'article à mettre à jour.
     * @param updatedArticleDTO les nouvelles informations de l'article.
     * @return l'article mis à jour sous forme de DTO, ou une réponse 404 si
     * l'article n'est pas trouvé.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO updatedArticleDTO) {
        ArticleDTO updatedArticle = articleService.updateArticle(id, updatedArticleDTO);
        return updatedArticle != null ? ResponseEntity.ok(updatedArticle) : ResponseEntity.notFound().build();
    }

    /**
     * Supprime un article par son ID.
     *
     * @param id l'ID de l'article à supprimer.
     * @return une réponse 200 (OK) si l'article a été supprimé, ou une réponse
     * 404 si l'article n'est pas trouvé.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        boolean deleted = articleService.deleteArticle(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
