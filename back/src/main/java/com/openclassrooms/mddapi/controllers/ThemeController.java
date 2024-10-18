package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    // Méthode pour convertir une entité Theme en DTO
    private ThemeDTO convertToDTO(Theme theme) {
        return new ThemeDTO(
                theme.getId(),
                theme.getTitle(),
                theme.getDescription(),
                theme.getCreatedAt(),
                theme.getUpdatedAt()
        );
    }

    // Méthode pour convertir un DTO en entité Theme
    private Theme convertToEntity(ThemeDTO themeDTO) {
        Theme theme = new Theme();
        theme.setId(themeDTO.getId());
        theme.setTitle(themeDTO.getTitle());
        theme.setDescription(themeDTO.getDescription());
        return theme;
    }

    // Récupérer tous les thèmes (retourne des DTO)
    @GetMapping
    public List<ThemeDTO> getAllThemes() {
        List<Theme> themes = themeService.getAllThemes();
        return themes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un thème par ID (retourne un DTO)
    @GetMapping("/{id}")
    public ResponseEntity<ThemeDTO> getThemeById(@PathVariable Long id) {
        Theme theme = themeService.getThemeById(id);
        return ResponseEntity.ok(convertToDTO(theme));
    }

    // Créer un nouveau thème (attend et retourne un DTO)
    @PostMapping
    public ResponseEntity<ThemeDTO> createTheme(@RequestBody ThemeDTO themeDTO) {
        Theme theme = convertToEntity(themeDTO);
        Theme newTheme = themeService.createTheme(theme);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newTheme));
    }

    // Mettre à jour un thème existant (attend et retourne un DTO)
    @PutMapping("/{id}")
    public ResponseEntity<ThemeDTO> updateTheme(@PathVariable Long id, @RequestBody ThemeDTO updatedThemeDTO) {
        Theme updatedTheme = convertToEntity(updatedThemeDTO);
        Theme savedTheme = themeService.updateTheme(id, updatedTheme);
        return ResponseEntity.ok(convertToDTO(savedTheme));
    }

    // Supprimer un thème
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.ok().build();
    }
}
