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
    public User register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Méthode pour authentifier un utilisateur et générer un token JWT
    public String login(User user) {
        UserDetails userDetails = loadUserByUsernameOrEmail(user.getUsername(), user.getEmail());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDetails.getUsername(),
                        user.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        Long userId = ((User) userDetails).getId(); // Assurez-vous que UserDetails est casté correctement pour obtenir l'ID
        String email = ((User) userDetails).getEmail(); // Assurez-vous que UserDetails est casté correctement pour obtenir l'email
        return jwtService.generateToken(userDetails, userId, email); // Passer l'ID de l'utilisateur et l'email
    }

    // Méthode pour récupérer l'utilisateur actuellement authentifié
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails userDetails) {
            String username = userDetails.getUsername();
            return userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));
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

    // Méthode pour changer le mot de passe de l'utilisateur
    public void changePassword(String currentPassword, String newPassword) throws Exception {
        User currentUser = getCurrentUser();
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new Exception("Mot de passe actuel incorrect.");
        }
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
    }
}
