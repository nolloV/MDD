package com.openclassrooms.mddapi.dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object (DTO) pour l'entité Article. Utilisé pour transférer les
 * données de l'article entre les couches de l'application.
 */
public class ArticleDTO {

    private Long id; // Identifiant unique de l'article
    private String title; // Titre de l'article
    private String content; // Contenu de l'article
    private String author; // Nom d'utilisateur de l'auteur
    private Long authorId; // ID de l'utilisateur auteur de l'article
    private LocalDateTime createdAt; // Date de création de l'article
    private LocalDateTime updatedAt; // Date de dernière mise à jour de l'article
    private List<String> comments; // Liste des commentaires associés à l'article

    /**
     * Constructeur par défaut.
     */
    public ArticleDTO() {
    }

    /**
     * Constructeur complet.
     *
     * @param id Identifiant unique de l'article
     * @param title Titre de l'article
     * @param content Contenu de l'article
     * @param author Nom d'utilisateur de l'auteur
     * @param authorId ID de l'utilisateur auteur de l'article
     * @param createdAt Date de création de l'article
     * @param updatedAt Date de dernière mise à jour de l'article
     * @param comments Liste des commentaires associés à l'article
     */
    public ArticleDTO(Long id, String title, String content, String author, Long authorId, LocalDateTime createdAt, LocalDateTime updatedAt, List<String> comments) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.comments = comments;
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
     * Récupère la date de création de l'article.
     *
     * @return la date de création de l'article
     */
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Définit la date de création de l'article.
     *
     * @param createdAt la date de création de l'article
     */
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
     * Définit la date de dernière mise à jour de l'article.
     *
     * @param updatedAt la date de dernière mise à jour de l'article
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Récupère la liste des commentaires associés à l'article.
     *
     * @return la liste des commentaires associés à l'article
     */
    public List<String> getComments() {
        return comments;
    }

    /**
     * Définit la liste des commentaires associés à l'article.
     *
     * @param comments la liste des commentaires associés à l'article
     */
    public void setComments(List<String> comments) {
        this.comments = comments;
    }
}
