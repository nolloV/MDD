package com.openclassrooms.mddapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.dtos.JwtResponseDTO;
import com.openclassrooms.mddapi.dtos.PasswordChangeRequestDTO;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.services.AuthService;
import com.openclassrooms.mddapi.services.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthService authService;

    // Méthode pour convertir un DTO en entité User
    private User convertToEntity(UserDTO userDTO) {
        User user = new User();
        user.setId(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        return user;
    }

    // Méthode pour convertir une entité User en DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null, // Ne pas inclure le mot de passe dans le DTO
                null // Ne pas inclure l'identifiant dans le DTO
        );
    }

    // Endpoint pour enregistrer un nouvel utilisateur
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        User user = convertToEntity(userDTO);
        User newUser = authService.register(user); // Appel du service d'authentification pour enregistrer l'utilisateur
        return ResponseEntity.ok(convertToDTO(newUser)); // Retourner les détails de l'utilisateur enregistré
    }

    // Endpoint pour se connecter
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginRequest) {
        // Charger les détails de l'utilisateur par nom d'utilisateur ou email
        UserDetails userDetails = authService.loadUserByUsernameOrEmail(loginRequest.getIdentifier(), loginRequest.getIdentifier());
        if (userDetails == null) {
            return ResponseEntity.status(403).body("Invalid username or email"); // Retourner une réponse 403 si l'utilisateur n'est pas trouvé
        }

        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), loginRequest.getPassword())
        );

        // Définir l'authentification dans le contexte de sécurité
        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long userId = ((User) userDetails).getId(); // Assurez-vous que UserDetails est casté correctement pour obtenir l'ID
        String email = authService.getEmailByUsernameOrEmail(loginRequest.getIdentifier());
        String jwt = jwtService.generateToken(userDetails, userId, email); // Générer le token JWT en passant l'ID de l'utilisateur et l'email

        // Retourner une réponse JSON contenant le token JWT
        return ResponseEntity.ok(new JwtResponseDTO(jwt));
    }

    // Endpoint pour changer le mot de passe
    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequestDTO passwordChangeRequest) {
        try {
            // Appel du service d'authentification pour changer le mot de passe
            authService.changePassword(passwordChangeRequest.getCurrentPassword(), passwordChangeRequest.getNewPassword());
            return ResponseEntity.ok("{\"message\": \"Mot de passe changé avec succès.\"}"); // Retourner une réponse de succès
        } catch (Exception e) {
            return ResponseEntity.status(400).body("{\"error\": \"" + e.getMessage() + "\"}"); // Retourner une réponse d'erreur en cas d'exception
        }
    }
}
