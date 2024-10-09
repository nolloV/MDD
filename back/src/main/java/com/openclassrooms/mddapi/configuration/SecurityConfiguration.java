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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                // Permettre l'accès à toutes les routes sans authentification
                .anyRequest().permitAll()
                )
                .csrf().disable() // Désactiver CSRF pour les appels API REST
                .cors();  // Activer CORS pour autoriser les requêtes cross-origin

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4200") // Origine des requêtes Angular
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Méthodes HTTP autorisées
                        .allowedHeaders("*")
                        .allowCredentials(true);  // Autoriser les cookies d'authentification si nécessaire
            }
        };
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password") // Mot de passe non encodé pour les tests
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
