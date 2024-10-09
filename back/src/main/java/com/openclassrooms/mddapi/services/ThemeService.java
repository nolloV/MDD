package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    // Récupérer tous les thèmes
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    // Récupérer un thème par ID
    public Theme getThemeById(Long id) {
        return themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + id));
    }

    // Créer un nouveau thème
    public Theme createTheme(Theme theme) {
        return themeRepository.save(theme);
    }

    // Mettre à jour un thème existant
    public Theme updateTheme(Long id, Theme updatedTheme) {
        Theme theme = getThemeById(id); // Utilise la méthode de récupération de thème
        theme.setTitle(updatedTheme.getTitle());
        theme.setDescription(updatedTheme.getDescription());
        return themeRepository.save(theme);
    }

    // Supprimer un thème
    public void deleteTheme(Long id) {
        Theme theme = getThemeById(id); // Utilise la méthode de récupération de thème
        themeRepository.delete(theme);
    }
}
