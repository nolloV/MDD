package com.openclassrooms.mddapi.dtos;

// Classe DTO pour encapsuler la réponse contenant le token JWT
public class JwtResponseDTO {

    // Champ pour stocker le token JWT
    private String token;

    // Constructeur pour initialiser le token
    public JwtResponseDTO(String token) {
        this.token = token;
    }

    // Getter pour récupérer le token
    public String getToken() {
        return token;
    }

    // Setter pour définir le token
    public void setToken(String token) {
        this.token = token;
    }
}
