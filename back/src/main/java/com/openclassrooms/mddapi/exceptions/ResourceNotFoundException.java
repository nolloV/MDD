package com.openclassrooms.mddapi.exceptions;

// Classe d'exception personnalisée pour indiquer qu'une ressource n'a pas été trouvée
public class ResourceNotFoundException extends RuntimeException {

    // Constructeur qui prend un message en paramètre et le passe à la classe parente RuntimeException
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
