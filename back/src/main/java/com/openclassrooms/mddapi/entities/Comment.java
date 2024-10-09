package com.openclassrooms.mddapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Column(length = 500)
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    @JsonBackReference // Empêche la sérialisation récursive des articles dans les commentaires
    private Article article;

    // Constructeur par défaut
    public Comment() {
    }

    // Constructeur complet
    public Comment(String username, String content, Article article) {
        this.username = username;
        this.content = content;
        this.article = article;
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

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    // Initialiser la date de création avant la persistance
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
