package com.openclassrooms.mddapi.services;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
// Service pour gérer les tokens JWT
public class JwtService {

    // Clé secrète pour signer les tokens JWT, injectée depuis les propriétés de configuration
    @Value("${security.jwt.secret-key}")
    private String secret;

    // Temps d'expiration des tokens JWT, injecté depuis les propriétés de configuration
    @Value("${security.jwt.expiration-time}")
    private long expirationTime;

    // Méthode pour obtenir la clé de signature à partir de la clé secrète
    private Key getSigningKey() {
        byte[] keyBytes = Base64.getDecoder().decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Méthode pour extraire le nom d'utilisateur du token JWT
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Méthode pour extraire la date d'expiration du token JWT
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // Méthode générique pour extraire un claim spécifique du token JWT
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Méthode pour extraire tous les claims du token JWT
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
    }

    // Méthode pour vérifier si le token JWT est expiré
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Méthode pour générer un token JWT pour un utilisateur donné
    public String generateToken(UserDetails userDetails, Long userId, String email) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", userId); // Inclure l'ID de l'utilisateur dans les claims
        claims.put("username", userDetails.getUsername());
        claims.put("email", email); // Inclure l'email dans les claims
        return createToken(claims, userDetails.getUsername());
    }

    // Méthode pour créer un token JWT avec les claims et le sujet donnés
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Méthode pour vérifier si le token JWT est valide pour un utilisateur donné
    public Boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
