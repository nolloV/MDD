package com.openclassrooms.mddapi.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.Date;

/**
 * Entité représentant un commentaire.
 */
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identifiant unique du commentaire

    private String username; // Nom d'utilisateur de l'auteur du commentaire

    @Column(length = 500)
    private String content; // Contenu du commentaire

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt; // Date de création du commentaire

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    @JsonBackReference // Empêche la sérialisation récursive des articles dans les commentaires
    private Article article; // Article associé au commentaire

    @Column(name = "author_id", nullable = false)
    private Long authorId; // ID de l'utilisateur auteur du commentaire

    /**
     * Constructeur par défaut.
     */
    public Comment() {
    }

    /**
     * Constructeur complet.
     *
     * @param username Nom d'utilisateur de l'auteur du commentaire
     * @param content Contenu du commentaire
     * @param article Article associé au commentaire
     * @param authorId ID de l'utilisateur auteur du commentaire
     */
    public Comment(String username, String content, Article article, Long authorId) {
        this.username = username;
        this.content = content;
        this.article = article;
        this.authorId = authorId;
    }

    // Getters et Setters
    /**
     * Récupère l'identifiant unique du commentaire.
     *
     * @return l'identifiant unique du commentaire
     */
    public Long getId() {
        return id;
    }

    /**
     * Définit l'identifiant unique du commentaire.
     *
     * @param id l'identifiant unique du commentaire
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Récupère le nom d'utilisateur de l'auteur du commentaire.
     *
     * @return le nom d'utilisateur de l'auteur du commentaire
     */
    public String getUsername() {
        return username;
    }

    /**
     * Définit le nom d'utilisateur de l'auteur du commentaire.
     *
     * @param username le nom d'utilisateur de l'auteur du commentaire
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Récupère le contenu du commentaire.
     *
     * @return le contenu du commentaire
     */
    public String getContent() {
        return content;
    }

    /**
     * Définit le contenu du commentaire.
     *
     * @param content le contenu du commentaire
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Récupère la date de création du commentaire.
     *
     * @return la date de création du commentaire
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * Définit la date de création du commentaire.
     *
     * @param createdAt la date de création du commentaire
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * Récupère l'article associé au commentaire.
     *
     * @return l'article associé au commentaire
     */
    public Article getArticle() {
        return article;
    }

    /**
     * Définit l'article associé au commentaire.
     *
     * @param article l'article associé au commentaire
     */
    public void setArticle(Article article) {
        this.article = article;
    }

    /**
     * Récupère l'ID de l'utilisateur auteur du commentaire.
     *
     * @return l'ID de l'utilisateur auteur du commentaire
     */
    public Long getAuthorId() {
        return authorId;
    }

    /**
     * Définit l'ID de l'utilisateur auteur du commentaire.
     *
     * @param authorId l'ID de l'utilisateur auteur du commentaire
     */
    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    /**
     * Méthode appelée avant la persistance du commentaire. Initialise la date
     * de création.
     */
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }
}
