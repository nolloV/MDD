package com.openclassrooms.mddapi.configuration;

import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.utils.UserDetailsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    private final UserRepository userRepository;

    // Constructeur pour injecter le UserRepository
    public ApplicationConfiguration(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Définition d'un bean UserDetailsService pour charger les détails de l'utilisateur
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(username)) // Recherche par email si le username n'est pas trouvé
                .map(UserDetailsImpl::build) // Conversion de User en UserDetailsImpl
                .orElseThrow(() -> new UsernameNotFoundException("User not found")); // Exception si l'utilisateur n'est pas trouvé
    }

    // Définition d'un bean BCryptPasswordEncoder pour encoder les mots de passe
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Définition d'un bean AuthenticationManager pour gérer l'authentification
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Définition d'un bean AuthenticationProvider pour fournir l'authentification
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // Utilisation du UserDetailsService défini précédemment
        authProvider.setPasswordEncoder(passwordEncoder()); // Utilisation du BCryptPasswordEncoder défini précédemment
        return authProvider;
    }
}
