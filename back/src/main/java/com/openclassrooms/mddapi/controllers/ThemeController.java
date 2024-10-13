package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @Autowired
    private ThemeService themeService;

    // Récupérer tous les thèmes (retourne des DTO)
    @GetMapping
    public List<ThemeDTO> getAllThemes() {
        return themeService.getAllThemes();
    }

    // Récupérer un thème par ID (retourne un DTO)
    @GetMapping("/{id}")
    public ResponseEntity<ThemeDTO> getThemeById(@PathVariable Long id) {
        ThemeDTO themeDTO = themeService.getThemeById(id);
        return ResponseEntity.ok(themeDTO);
    }

    // Créer un nouveau thème (attend et retourne un DTO)
    @PostMapping
    public ResponseEntity<ThemeDTO> createTheme(@RequestBody ThemeDTO themeDTO) {
        ThemeDTO newThemeDTO = themeService.createTheme(themeDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newThemeDTO);
    }

    // Mettre à jour un thème existant (attend et retourne un DTO)
    @PutMapping("/{id}")
    public ResponseEntity<ThemeDTO> updateTheme(@PathVariable Long id, @RequestBody ThemeDTO updatedThemeDTO) {
        ThemeDTO savedThemeDTO = themeService.updateTheme(id, updatedThemeDTO);
        return ResponseEntity.ok(savedThemeDTO);
    }

    // Supprimer un thème
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.ok().build();
    }
}
