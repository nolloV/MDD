package com.openclassrooms.mddapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.entities.User;

@Repository
// Interface de repository pour l'entité User
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode pour trouver un utilisateur par son nom d'utilisateur
    Optional<User> findByUsername(String username);

    // Méthode pour trouver un utilisateur par son email
    Optional<User> findByEmail(String email);
}
