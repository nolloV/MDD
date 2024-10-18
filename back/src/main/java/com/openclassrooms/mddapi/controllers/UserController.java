package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.dtos.ArticleDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.ThemeDTO;
import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Méthode pour convertir une entité User en DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getThemes().stream().map(this::convertToDTO).collect(Collectors.toSet()), // Utiliser Collectors.toSet()
                null, // Le mot de passe ne doit pas être renvoyé dans le DTO
                null // L'identifiant ne doit pas être renvoyé dans le DTO
        );
    }

    // Méthode pour convertir un DTO en entité User
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

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

    // Méthode pour convertir une entité Article en DTO
    private ArticleDTO convertToArticleDTO(Article article) {
        return new ArticleDTO(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getAuthor(),
                article.getAuthorId(),
                article.getTheme().getId(),
                article.getCreatedAt(),
                article.getUpdatedAt(),
                article.getComments() != null
                ? article.getComments().stream().map(comment -> comment.getContent()).collect(Collectors.toList())
                : List.of()
        );
    }

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<UserDTO> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDTO(user));
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User newUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(newUser));
    }

    // Mettre à jour un utilisateur
    @PutMapping("/me")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO updatedUserDTO) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User updatedUser = convertToEntity(updatedUserDTO);
        User savedUser = userService.updateUserByUsername(currentUsername, updatedUser);
        return ResponseEntity.ok(convertToDTO(savedUser));
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
        User updatedUser = userService.subscribeToTheme(userId, themeId);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    // Endpoint pour se désabonner d'un thème
    @PostMapping("/{userId}/unsubscribe/{themeId}")
    public ResponseEntity<UserDTO> unsubscribeFromTheme(@PathVariable Long userId, @PathVariable Long themeId) {
        User updatedUser = userService.unsubscribeFromTheme(userId, themeId);
        return ResponseEntity.ok(convertToDTO(updatedUser));
    }

    // Endpoint pour récupérer les articles des thèmes abonnés
    @GetMapping("/{userId}/subscribed-articles")
    public ResponseEntity<List<ArticleDTO>> getArticlesForSubscribedThemes(@PathVariable Long userId) {
        List<Article> articles = userService.getArticlesForSubscribedThemes(userId);
        List<ArticleDTO> articleDTOs = articles.stream().map(this::convertToArticleDTO).collect(Collectors.toList());
        return ResponseEntity.ok(articleDTOs);
    }
}
