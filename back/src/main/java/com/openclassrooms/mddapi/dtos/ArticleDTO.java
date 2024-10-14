package com.openclassrooms.mddapi.dtos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Champs pour les commentaires
    private List<String> comments;  // Pour lister les commentaires par leur contenu

    // Constructeur par défaut
    public ArticleDTO() {
        this.comments = new ArrayList<>(); // Initialiser à une liste vide par défaut
    }

    // Constructeur complet pour simplifier la création
    public ArticleDTO(Long id, String title, String content, String author, LocalDateTime createdAt, LocalDateTime updatedAt, List<String> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments != null ? comments : new ArrayList<>(); // Initialiser la liste des commentaires si elle est null
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments != null ? comments : new ArrayList<>(); // S'assurer que la liste ne soit jamais null
    }
}
