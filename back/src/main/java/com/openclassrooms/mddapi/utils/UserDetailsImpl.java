package com.openclassrooms.mddapi.utils;

import java.util.Collection; // Import de la classe User
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.openclassrooms.mddapi.entities.User;

// Implémentation de UserDetails pour intégrer les détails de l'utilisateur dans Spring Security
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    // Champs pour stocker les détails de l'utilisateur
    private final Long id; // ID de l'utilisateur
    private final String username; // Nom d'utilisateur
    private final String email; // Email de l'utilisateur
    private final String password; // Mot de passe de l'utilisateur

    // Constructeur pour initialiser les champs
    public UserDetailsImpl(Long id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // Méthode statique pour construire une instance de UserDetailsImpl à partir d'un objet User
    public static UserDetailsImpl build(User user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword()
        );
    }

    // Méthode pour obtenir les autorités (rôles) de l'utilisateur
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null; // Pas de rôle pour l'instant, donc null
    }

    // Getter pour le mot de passe
    @Override
    public String getPassword() {
        return password;
    }

    // Getter pour le nom d'utilisateur
    @Override
    public String getUsername() {
        return username;
    }

    // Getter pour l'email
    public String getEmail() {
        return email;
    }

    // Méthode pour vérifier si le compte n'est pas expiré
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Méthode pour vérifier si le compte n'est pas verrouillé
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Méthode pour vérifier si les informations d'identification ne sont pas expirées
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Méthode pour vérifier si le compte est activé
    @Override
    public boolean isEnabled() {
        return true;
    }

    // Méthode equals pour comparer les objets UserDetailsImpl
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }

    // Méthode hashCode pour générer un code de hachage basé sur l'ID
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
