package com.openclassrooms.mddapi.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité représentant un article.
 */
@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique de l'article

    @Column(nullable = false, length = 255)
    private String title; // Titre de l'article

    @Column(columnDefinition = "TEXT")
    private String content; // Contenu de l'article

    @Column(nullable = false)
    private String author; // Nom d'utilisateur de l'auteur

    @Column(nullable = false)
    private Long authorId; // ID de l'utilisateur auteur de l'article

    @ManyToOne
    @JoinColumn(name = "theme_id", nullable = false)
    private Theme theme; // Thème associé à l'article

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // Date de création de l'article

    @Column(name = "updated_at")
    private LocalDateTime updatedAt; // Date de dernière mise à jour de l'article

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments = new ArrayList<>(); // Liste des commentaires associés à l'article

    /**
     * Constructeur par défaut.
     */
    public Article() {
    }

    /**
     * Constructeur complet.
     *
     * @param title Titre de l'article
     * @param content Contenu de l'article
     * @param author Nom d'utilisateur de l'auteur
     * @param authorId ID de l'utilisateur auteur de l'article
     * @param theme Thème associé à l'article
     */
    public Article(String title, String content, String author, Long authorId, Theme theme) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorId = authorId;
        this.theme = theme;
        this.comments = new ArrayList<>();
    }

    // Getters et Setters
    /**
     * Récupère l'identifiant unique de l'article.
     *
     * @return l'identifiant unique de l'article
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique de l'article.
     *
     * @param id l'identifiant unique de l'article
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le titre de l'article.
     *
     * @return le titre de l'article
     */
    public String getTitle() {
        return title;
    }

    /**
     * Définit le titre de l'article.
     *
     * @param title le titre de l'article
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Récupère le contenu de l'article.
     *
     * @return le contenu de l'article
     */
    public String getContent() {
        return content;
    }

    /**
     * Définit le contenu de l'article.
     *
     * @param content le contenu de l'article
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Récupère le nom d'utilisateur de l'auteur de l'article.
     *
     * @return le nom d'utilisateur de l'auteur de l'article
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Définit le nom d'utilisateur de l'auteur de l'article.
     *
     * @param author le nom d'utilisateur de l'auteur de l'article
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * Récupère l'ID de l'utilisateur auteur de l'article.
     *
     * @return l'ID de l'utilisateur auteur de l'article
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * Définit l'ID de l'utilisateur auteur de l'article.
     *
     * @param authorId l'ID de l'utilisateur auteur de l'article
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * Récupère le thème associé à l'article.
     *
     * @return le thème associé à l'article
     */
    public Theme getTheme() {
        return theme;
    }

    /**
     * Définit le thème associé à l'article.
     *
     * @param theme le thème associé à l'article
     */
    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    /**
     * Récupère la date de création de l'article.
     *
     * @return la date de création de l'article
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Récupère la date de dernière mise à jour de l'article.
     *
     * @return la date de dernière mise à jour de l'article
     */
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Récupère la liste des commentaires associés à l'article.
     *
     * @return la liste des commentaires associés à l'article
     */
    public List<Comment> getComments() {
        return comments;
    }

    /**
     * Définit la liste des commentaires associés à l'article.
     *
     * @param comments la liste des commentaires associés à l'article
     */
    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    /**
     * Méthode appelée avant la persistance de l'article. Initialise les dates
     * de création et de mise à jour.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Méthode appelée avant la mise à jour de l'article. Met à jour la date de
     * dernière mise à jour.
     */
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
