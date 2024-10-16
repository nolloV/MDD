package com.openclassrooms.mddapi.configuration;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.openclassrooms.mddapi.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Constructeur pour injecter JwtService et UserDetailsService
    public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailsService) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    // Méthode pour filtrer chaque requête HTTP
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        // Récupérer l'en-tête Authorization de la requête
        final String authHeader = request.getHeader("Authorization");

        // Vérifier si l'en-tête est présent et commence par "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extraire le token JWT de l'en-tête
        final String jwt = authHeader.substring(7);
        // Extraire le nom d'utilisateur du token JWT
        final String username = jwtService.extractUsername(jwt);
        // Récupérer l'authentification actuelle du contexte de sécurité
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Vérifier si le nom d'utilisateur est présent et l'utilisateur n'est pas déjà authentifié
        if (username != null && authentication == null) {
            // Charger les détails de l'utilisateur à partir du nom d'utilisateur
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Vérifier si le token JWT est valide
            if (jwtService.isTokenValid(jwt, userDetails)) {
                // Créer un objet UsernamePasswordAuthenticationToken avec les détails de l'utilisateur
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                // Définir les détails de l'authentification
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Définir l'authentification dans le contexte de sécurité
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continuer le filtrage de la requête
        filterChain.doFilter(request, response);
    }
}
