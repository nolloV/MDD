package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.dtos.UserDTO;
import com.openclassrooms.mddapi.entities.User;
import com.openclassrooms.mddapi.repositories.UserRepository;
import com.openclassrooms.mddapi.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Récupérer tous les utilisateurs
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    // Récupérer un utilisateur par ID
    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        return convertToDTO(user);
    }

    // Créer un nouvel utilisateur
    public UserDTO createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword())); // Encode le mot de passe

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Mettre à jour un utilisateur
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        user.setUsername(updatedUserDTO.getUsername());
        user.setEmail(updatedUserDTO.getEmail());
        if (updatedUserDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updatedUserDTO.getPassword())); // Mettre à jour le mot de passe
        }

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
    }

    // Supprimer un utilisateur
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.delete(user);
    }

    // Charger un utilisateur par nom d'utilisateur ou email
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

    // Conversion Entité -> DTO
    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                null, // Le mot de passe ne doit pas être renvoyé dans le DTO
                null
        );
    }
}
