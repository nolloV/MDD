package com.openclassrooms.mddapi.dtos;

import java.util.Date;

/**
 * Data Transfer Object (DTO) pour l'entité Comment. Utilisé pour transférer les
 * données du commentaire entre les couches de l'application.
 */
public class CommentDTO {

    private Long id; // Identifiant unique du commentaire
    private String username; // Nom d'utilisateur de l'auteur du commentaire
    private String content; // Contenu du commentaire
    private Date createdAt; // Date de création du commentaire
    private Long articleId;  // Référence à l'ID de l'article pour simplifier
    private Long authorId; // ID de l'utilisateur auteur du commentaire

    /**
     * Constructeur par défaut.
     */
    public CommentDTO() {
    }

    /**
     * Constructeur complet.
     *
     * @param id Identifiant unique du commentaire
     * @param username Nom d'utilisateur de l'auteur du commentaire
     * @param content Contenu du commentaire
     * @param createdAt Date de création du commentaire
     * @param articleId Référence à l'ID de l'article
     * @param authorId ID de l'utilisateur auteur du commentaire
     */
    public CommentDTO(Long id, String username, String content, Date createdAt, Long articleId, Long authorId) {
        this.id = id;
        this.username = username;
        this.content = content;
        this.createdAt = createdAt;
        this.articleId = articleId;
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
     * Récupère l'ID de l'article associé au commentaire.
     *
     * @return l'ID de l'article associé au commentaire
     */
    public Long getArticleId() {
        return articleId;
    }

    /**
     * Définit l'ID de l'article associé au commentaire.
     *
     * @param articleId l'ID de l'article associé au commentaire
     */
    public void setArticleId(Long articleId) {
        this.articleId = articleId;
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
}
