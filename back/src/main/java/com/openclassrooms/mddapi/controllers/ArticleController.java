package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.services.ArticleService;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private ThemeService themeService;

    /**
     * Convertit une entité Article en DTO ArticleDTO.
     *
     * @param article l'entité Article à convertir.
     * @return le DTO ArticleDTO correspondant.
     */
    private ArticleDTO convertToDTO(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getAuthorId(),
                article.getTheme().getId(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getComments() != null
                ? article.getComments().stream().map(comment -> comment.getContent()).collect(Collectors.toList())
                : List.of()
        );
    }

    /**
     * Convertit un DTO ArticleDTO en entité Article.
     *
     * @param articleDTO le DTO ArticleDTO à convertir.
     * @return l'entité Article correspondante.
     */
    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setAuthor(articleDTO.getAuthor());
        article.setAuthorId(articleDTO.getAuthorId());
        Theme theme = themeService.getThemeById(articleDTO.getThemeId());
        article.setTheme(theme);
        return article;
    }

    /**
     * Récupère tous les articles.
     *
     * @return une liste de tous les articles sous forme de DTO.
     */
    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
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
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(convertToDTO(article));
    }

    /**
     * Crée un nouvel article.
     *
     * @param articleDTO les informations de l'article à créer.
     * @return l'article créé sous forme de DTO avec un statut 201 (Created).
     */
    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        Article newArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newArticle));
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
        Article updatedArticle = convertToEntity(updatedArticleDTO);
        Article article = articleService.updateArticle(id, updatedArticle);
        return ResponseEntity.ok(convertToDTO(article));
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
