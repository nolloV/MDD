package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.entities.Theme;
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

    // Récupérer tous les thèmes
    @GetMapping
    public List<Theme> getAllThemes() {
        return themeService.getAllThemes();
    }

    // Récupérer un thème par ID
    @GetMapping("/{id}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Long id) {
        Theme theme = themeService.getThemeById(id);
        return ResponseEntity.ok(theme);
    }

    // Créer un nouveau thème
    @PostMapping
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) {
        Theme newTheme = themeService.createTheme(theme);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTheme);
    }

    // Mettre à jour un thème existant
    @PutMapping("/{id}")
    public ResponseEntity<Theme> updateTheme(@PathVariable Long id, @RequestBody Theme updatedTheme) {
        Theme savedTheme = themeService.updateTheme(id, updatedTheme);
        return ResponseEntity.ok(savedTheme);
    }

    // Supprimer un thème
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        themeService.deleteTheme(id);
        return ResponseEntity.ok().build();
    }
}
