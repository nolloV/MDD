package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.entities.Article;
import com.openclassrooms.mddapi.entities.Theme;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import com.openclassrooms.mddapi.repositories.ThemeRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ThemeRepository themeRepository;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Récupérer tous les utilisateurs.
     *
     * @return une liste de tous les utilisateurs.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Récupérer un utilisateur par ID.
     *
     * @param id l'ID de l'utilisateur à récupérer.
     * @return l'utilisateur correspondant.
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    /**
     * Créer un nouvel utilisateur.
     *
     * @param user les informations de l'utilisateur à créer.
     * @return l'utilisateur créé.
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode le mot de passe
        return userRepository.save(user);
    }

    /**
     * Mettre à jour un utilisateur.
     *
     * @param id l'ID de l'utilisateur à mettre à jour.
     * @param updatedUser les nouvelles informations de l'utilisateur.
     * @return l'utilisateur mis à jour.
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Mettre à jour le mot de passe
        }

        return userRepository.save(user);
    }

    /**
     * Mettre à jour un utilisateur par nom d'utilisateur.
     *
     * @param username le nom d'utilisateur de l'utilisateur à mettre à jour.
     * @param updatedUser les nouvelles informations de l'utilisateur.
     * @return l'utilisateur mis à jour.
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public User updateUserByUsername(String username, User updatedUser) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        if (updatedUser.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUser.getPassword())); // Mettre à jour le mot de passe
        }

        return userRepository.save(user);
    }

    /**
     * Supprimer un utilisateur.
     *
     * @param id l'ID de l'utilisateur à supprimer.
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

    /**
     * Charger un utilisateur par nom d'utilisateur ou email.
     *
     * @param username le nom d'utilisateur.
     * @param email l'email.
     * @return les détails de l'utilisateur.
     * @throws UsernameNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public UserDetails loadUserByUsernameOrEmail(String username, String email) {
        User user = userRepository.findByUsername(username)
                .or(() -> userRepository.findByEmail(email))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email: " + username + " / " + email));
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities("USER")
                .build();
    }

    /**
     * Abonner un utilisateur à un thème.
     *
     * @param userId l'ID de l'utilisateur.
     * @param themeId l'ID du thème.
     * @return l'utilisateur mis à jour.
     * @throws ResourceNotFoundException si l'utilisateur ou le thème n'est pas
     * trouvé.
     */
    public User subscribeToTheme(Long userId, Long themeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + themeId));

        user.getThemes().add(theme);
        return userRepository.save(user);
    }

    /**
     * Désabonner un utilisateur d'un thème.
     *
     * @param userId l'ID de l'utilisateur.
     * @param themeId l'ID du thème.
     * @return l'utilisateur mis à jour.
     * @throws ResourceNotFoundException si l'utilisateur ou le thème n'est pas
     * trouvé.
     */
    public User unsubscribeFromTheme(Long userId, Long themeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + userId));
        Theme theme = themeRepository.findById(themeId)
                .orElseThrow(() -> new ResourceNotFoundException("Theme not found with id " + themeId));

        user.getThemes().remove(theme);
        return userRepository.save(user);
    }

    /**
     * Récupérer les articles des thèmes auxquels l'utilisateur est abonné.
     *
     * @param userId l'ID de l'utilisateur.
     * @return une liste des articles.
     * @throws ResourceNotFoundException si l'utilisateur n'est pas trouvé.
     */
    public List<Article> getArticlesForSubscribedThemes(Long userId) {
        return articleService.getArticlesForSubscribedThemes(userId);
    }
}
