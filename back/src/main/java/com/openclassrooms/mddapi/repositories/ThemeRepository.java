package com.openclassrooms.mddapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.openclassrooms.mddapi.entities.Theme;

@Repository
// Interface de repository pour l'entité Theme
public interface ThemeRepository extends JpaRepository<Theme, Long> {

    // Méthode pour trouver un thème par son titre
    Optional<Theme> findByTitle(String title);
}
