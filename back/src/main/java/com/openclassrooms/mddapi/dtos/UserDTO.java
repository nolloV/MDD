package com.openclassrooms.mddapi.dtos;

import java.util.Set;

public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private Set<Long> subscribedThemes; // Par exemple pour les thèmes auxquels l'utilisateur est abonné
    private String password; // Ajout du mot de passe pour le login et la création d'utilisateur

    // Constructeur par défaut
    public UserDTO() {
    }

    // Constructeur complet
    public UserDTO(Long id, String username, String email, Set<Long> subscribedThemes, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.subscribedThemes = subscribedThemes;
        this.password = password;
    }

    // Nouveau constructeur avec seulement id, username, email, et mot de passe
    public UserDTO(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public Set<Long> getSubscribedThemes() {
        return subscribedThemes;
    }

    public void setSubscribedThemes(Set<Long> subscribedThemes) {
        this.subscribedThemes = subscribedThemes;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
