package com.openclassrooms.mddapi.configuration;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    // Constructeur pour injecter JwtAuthenticationFilter et AuthenticationProvider
    public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, AuthenticationProvider authenticationProvider) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authenticationProvider = authenticationProvider;
    }

    // Définition du bean SecurityFilterChain pour configurer la sécurité HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Désactiver CSRF pour les appels API REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Activer CORS pour autoriser les requêtes cross-origin
                .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/**").permitAll() // Permettre l'accès sans authentification aux endpoints d'authentification
                .requestMatchers("/users/me").authenticated() // Exiger une authentification pour la mise à jour des informations de l'utilisateur connecté
                .requestMatchers("/users/**").authenticated() // Exiger une authentification pour les endpoints users
                .requestMatchers("/themes/**").authenticated() // Exiger une authentification pour les endpoints themes
                .requestMatchers("/articles/**").authenticated() // Exiger une authentification pour les endpoints articles
                .anyRequest().authenticated() // Exiger une authentification pour toutes les autres requêtes
                )
                .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Utiliser des sessions sans état
                .authenticationProvider(authenticationProvider) // Utiliser le fournisseur d'authentification défini
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Ajouter le filtre JWT avant le filtre d'authentification par nom d'utilisateur et mot de passe

        return http.build();
    }

    // Définition du bean CorsConfigurationSource pour configurer les règles CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Autoriser les requêtes provenant de cette origine
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE")); // Autoriser ces méthodes HTTP
        configuration.setAllowedHeaders(List.of("*")); // Autoriser tous les en-têtes
        configuration.setAllowCredentials(true); // Autoriser l'envoi des cookies

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Appliquer cette configuration à toutes les routes
        return source;
    }

    // Définition du bean WebMvcConfigurer pour configurer les règles CORS au niveau MVC
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // Autoriser les requêtes provenant de cette origine
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Autoriser ces méthodes HTTP
                        .allowedHeaders("*") // Autoriser tous les en-têtes
                        .allowCredentials(true); // Autoriser l'envoi des cookies
            }
        };
    }
}
