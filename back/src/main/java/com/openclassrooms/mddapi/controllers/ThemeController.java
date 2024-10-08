package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/themes")
public class ThemeController {

    @Autowired
    private ThemeRepository themeRepository;

    // Récupérer tous les thèmes
    @GetMapping
    public List<Theme> getAllThemes() {
        return themeRepository.findAll();
    }

    // Récupérer un thème par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Theme> getThemeById(@PathVariable Long id) {
        return themeRepository.findById(id)
                .map(theme -> ResponseEntity.ok().body(theme))
                .orElse(ResponseEntity.notFound().build());
    }

    // Créer un nouveau thème
    @PostMapping
    public ResponseEntity<Theme> createTheme(@RequestBody Theme theme) {
        Theme newTheme = themeRepository.save(theme);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTheme);
    }

    // Mettre à jour un thème existant
    @PutMapping("/{id}")
    public ResponseEntity<Theme> updateTheme(@PathVariable Long id, @RequestBody Theme updatedTheme) {
        return themeRepository.findById(id)
                .map(theme -> {
                    theme.setTitle(updatedTheme.getTitle());
                    theme.setDescription(updatedTheme.getDescription());
                    Theme savedTheme = themeRepository.save(theme);
                    return ResponseEntity.ok().body(savedTheme);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un thème
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTheme(@PathVariable Long id) {
        return themeRepository.findById(id)
                .map(theme -> {
                    themeRepository.delete(theme);
                    return ResponseEntity.ok().<Void>build();  // Assure un retour de type ResponseEntity<Void>
                })
                .orElse(ResponseEntity.notFound().build());
    }

}
