package com.openclassrooms.mddapi.dtos;

import java.util.Date;

public class CommentDTO {

    private Long id;
    private String username;
    private String content;
    private Date createdAt;
    private Long articleId;  // Référence à l'ID de l'article pour simplifier

    // Constructeur par défaut
    public CommentDTO() {
    }

    // Constructeur complet
    public CommentDTO(Long id, String username, String content, Date createdAt, Long articleId) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.articleId = articleId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}
