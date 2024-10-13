package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemeService {

    @Autowired
    private ThemeRepository themeRepository;

    // Récupérer tous les thèmes (convertis en DTO)
    public List<ThemeDTO> getAllThemes() {
        List<Theme> themes = themeRepository.findAll();
        return themes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un thème par ID (converti en DTO)
    public ThemeDTO getThemeById(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + id));
        return convertToDTO(theme);
    }

    // Créer un nouveau thème (convertir DTO en entité)
    public ThemeDTO createTheme(ThemeDTO themeDTO) {
        Theme theme = convertToEntity(themeDTO);
        Theme savedTheme = themeRepository.save(theme);
        return convertToDTO(savedTheme);
    }

    // Mettre à jour un thème existant (convertir DTO en entité)
    public ThemeDTO updateTheme(Long id, ThemeDTO updatedThemeDTO) {
        // Récupérer l'entité Theme par son ID
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + id));
        theme.setTitle(updatedThemeDTO.getTitle());
        theme.setDescription(updatedThemeDTO.getDescription());
        Theme savedTheme = themeRepository.save(theme);
        return convertToDTO(savedTheme);
    }

    // Supprimer un thème
    public void deleteTheme(Long id) {
        Theme theme = themeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + id));
        themeRepository.delete(theme);
    }

    // Conversion Entité -> DTO
    private ThemeDTO convertToDTO(Theme theme) {
        return new ThemeDTO(
                theme.getId(),
                theme.getTitle(),
                theme.getDescription(),
                theme.getCreatedAt(),
                theme.getUpdatedAt()
        );
    }

    // Conversion DTO -> Entité
    private Theme convertToEntity(ThemeDTO themeDTO) {
        Theme theme = new Theme();
        theme.setId(themeDTO.getId());
        theme.setTitle(themeDTO.getTitle());
        theme.setDescription(themeDTO.getDescription());
        // Suppression de la gestion manuelle des dates
        return theme;
    }
}
