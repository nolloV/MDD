package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        UserDTO userDTO = userService.getUserById(id);
        return ResponseEntity.ok(userDTO);
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        // Le mot de passe est déjà inclus dans userDTO
        UserDTO newUserDTO = userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUserDTO);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updatedUserDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        UserDTO savedUserDTO = userService.updateUserByUsername(currentUsername, updatedUserDTO);
        return ResponseEntity.ok(savedUserDTO);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    // Endpoint pour s'abonner à un thème
    @PostMapping("/{userId}/subscribe/{themeId}")
    public ResponseEntity<UserDTO> subscribeToTheme(@PathVariable Long userId, @PathVariable Long themeId) {
        UserDTO updatedUser = userService.subscribeToTheme(userId, themeId);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint pour se désabonner d'un thème
    @PostMapping("/{userId}/unsubscribe/{themeId}")
    public ResponseEntity<UserDTO> unsubscribeFromTheme(@PathVariable Long userId, @PathVariable Long themeId) {
        UserDTO updatedUser = userService.unsubscribeFromTheme(userId, themeId);
        return ResponseEntity.ok(updatedUser);
    }

    // Endpoint pour récupérer les articles des thèmes abonnés
    @GetMapping("/{userId}/subscribed-articles")
    public ResponseEntity<List<ArticleDTO>> getArticlesForSubscribedThemes(@PathVariable Long userId) {
        List<ArticleDTO> articles = userService.getArticlesForSubscribedThemes(userId);
        return ResponseEntity.ok(articles);
    }
}
