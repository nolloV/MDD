package com.openclassrooms.mddapi.dtos;

import java.util.Set;

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private Set<String> subscribedThemes; // Utiliser Set<String> pour les titres des thèmes
    private String password; // Ajout du mot de passe pour le login et la création d'utilisateur
    private String identifier; // Ajout du champ identifier

    // Constructeur par défaut
    public UserDTO() {
    }

    // Constructeur complet
    public UserDTO(Long id, String username, String email, Set<String> subscribedThemes, String password, String identifier) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.subscribedThemes = subscribedThemes;
        this.password = password;
        this.identifier = identifier;
    }

    // Nouveau constructeur avec seulement id, username, email, mot de passe et identifier
    public UserDTO(Long id, String username, String email, String password, String identifier) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.identifier = identifier;
    }

    // Getters et setters
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<String> getSubscribedThemes() {
        return subscribedThemes;
    }

    public void setSubscribedThemes(Set<String> subscribedThemes) {
        this.subscribedThemes = subscribedThemes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
