package com.openclassrooms.mddapi.services;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    // Inscription d'un nouvel utilisateur
    public UserDTO register(UserDTO userDTO) {
        User newUser = new User();
        newUser.setUsername(userDTO.getUsername());
        newUser.setEmail(userDTO.getEmail());
        newUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User savedUser = userRepository.save(newUser);

        userDTO.setId(savedUser.getId());
        userDTO.setPassword(null); // Ne pas retourner le mot de passe
        return userDTO;
    }

    // Méthode pour authentifier un utilisateur et générer un token JWT
    public String login(UserDTO loginRequest) {
        UserDetails userDetails = loadUserByUsernameOrEmail(loginRequest.getUsername(), loginRequest.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long userId = ((User) userDetails).getId(); // Assurez-vous que UserDetails est casté correctement pour obtenir l'ID
        String email = ((User) userDetails).getEmail(); // Assurez-vous que UserDetails est casté correctement pour obtenir l'email
        return jwtService.generateToken(userDetails, userId, email); // Passer l'ID de l'utilisateur et l'email
    }

    // Méthode pour récupérer l'utilisateur actuellement authentifié
    public UserDTO getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            return new UserDTO(user.getId(), user.getUsername(), user.getEmail(), null, null);
        } else {
            throw new AuthenticationCredentialsNotFoundException("User not authenticated");
        }
    }

    // Méthode pour charger un utilisateur par nom d'utilisateur ou email
    public UserDetails loadUserByUsernameOrEmail(String username, String email) {
        return userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username + " / " + email));
    }

    // Méthode pour récupérer l'email d'un utilisateur par nom d'utilisateur ou email
    public String getEmailByUsernameOrEmail(String identifier) {
        return userRepository.findByUsername(identifier)
                .or(() -> userRepository.findByEmail(identifier))
                .map(User::getEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + identifier));
    }
}
