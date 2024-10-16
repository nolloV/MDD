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

import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.dtos.PasswordChangeRequestDTO;
import com.openclassrooms.mddapi.dtos.JwtResponseDTO;
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

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserDTO userDTO) {
        UserDTO newUserDTO = authService.register(userDTO);
        return ResponseEntity.ok(newUserDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO loginRequest) {
        UserDetails userDetails = authService.loadUserByUsernameOrEmail(loginRequest.getIdentifier(), loginRequest.getIdentifier());
        if (userDetails == null) {
            return ResponseEntity.status(403).body("Invalid username or email");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDetails.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long userId = ((User) userDetails).getId(); // Assurez-vous que UserDetails est casté correctement pour obtenir l'ID
        String email = authService.getEmailByUsernameOrEmail(loginRequest.getIdentifier());
        String jwt = jwtService.generateToken(userDetails, userId, email); // Passer l'ID de l'utilisateur et l'email

        // Retourner une réponse JSON contenant le token JWT
        return ResponseEntity.ok(new JwtResponseDTO(jwt));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody PasswordChangeRequestDTO passwordChangeRequest) {
        try {
            authService.changePassword(passwordChangeRequest.getCurrentPassword(), passwordChangeRequest.getNewPassword());
            return ResponseEntity.ok("{\"message\": \"Mot de passe changé avec succès.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}
