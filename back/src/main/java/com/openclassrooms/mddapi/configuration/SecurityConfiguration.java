package com.openclassrooms.mddapi.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfiguration {

    // Configuration de la chaîne de sécurité
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/themes/**").permitAll() // Autoriser l'accès aux routes /themes
                .anyRequest().authenticated() // Authentification requise pour les autres routes
                )
                .csrf().disable() // Désactiver CSRF pour les appels API REST
                .cors();  // Activer CORS pour autoriser les requêtes cross-origin

        return http.build();
    }

    // Configuration CORS pour permettre les requêtes depuis Angular
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // Autoriser les requêtes provenant de l'application Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP autorisées
                        .allowedHeaders("*")
                        .allowCredentials(true);  // Autoriser l'envoi des cookies d'authentification
            }
        };
    }

    // Gestion des utilisateurs en mémoire pour l'authentification basique
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // Mot de passe non encodé
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
