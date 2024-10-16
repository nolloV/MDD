package com.openclassrooms.mddapi.dtos;

// Classe DTO pour encapsuler les données de la requête de changement de mot de passe
public class PasswordChangeRequestDTO {

    // Champ pour stocker le mot de passe actuel
    private String currentPassword;

    // Champ pour stocker le nouveau mot de passe
    private String newPassword;

    // Getter pour récupérer le mot de passe actuel
    public String getCurrentPassword() {
        return currentPassword;
    }

    // Setter pour définir le mot de passe actuel
    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    // Getter pour récupérer le nouveau mot de passe
    public String getNewPassword() {
        return newPassword;
    }

    // Setter pour définir le nouveau mot de passe
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    // Méthode toString pour représenter l'objet sous forme de chaîne de caractères
    @Override
    public String toString() {
        return "PasswordChangeRequestDTO{"
                + "currentPassword='" + currentPassword + '\''
                + ", newPassword='" + newPassword + '\''
                + '}';
    }
}
