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

    @GetMapping
    public List<ArticleDTO> getAllArticles() {
        return articleService.getAllArticles().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleDTO> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id);
        return ResponseEntity.ok(convertToDTO(article));
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> createArticle(@RequestBody ArticleDTO articleDTO) {
        Article article = convertToEntity(articleDTO);
        Article newArticle = articleService.createArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newArticle));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> updateArticle(@PathVariable Long id, @RequestBody ArticleDTO updatedArticleDTO) {
        Article updatedArticle = convertToEntity(updatedArticleDTO);
        Article article = articleService.updateArticle(id, updatedArticle);
        return ResponseEntity.ok(convertToDTO(article));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable Long id) {
        boolean deleted = articleService.deleteArticle(id);
        return deleted ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}
