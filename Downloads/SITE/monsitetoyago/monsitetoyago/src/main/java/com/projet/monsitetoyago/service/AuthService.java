package com.projet.monsitetoyago.service;


import com.projet.monsitetoyago.dto.LoginRequest;
import com.projet.monsitetoyago.dto.LoginResponse;
import com.projet.monsitetoyago.entity.User;
import com.projet.monsitetoyago.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public LoginResponse authenticate(LoginRequest loginRequest) {
        System.out.println("🔐 Authentification en cours pour: " + loginRequest.getUsername());

        Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());

        if (userOptional.isEmpty()) {
            System.out.println("❌ Utilisateur non trouvé: " + loginRequest.getUsername());
            return new LoginResponse(false, "Utilisateur non trouvé");
        }

        User user = userOptional.get();

        if (!user.getActive()) {
            System.out.println("❌ Compte désactivé: " + loginRequest.getUsername());
            return new LoginResponse(false, "Compte désactivé");
        }

        // Vérification du mot de passe
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            System.out.println("❌ Mot de passe incorrect pour: " + loginRequest.getUsername());
            return new LoginResponse(false, "Mot de passe incorrect");
        }

        // Génération du token
        String token = generateToken();
        System.out.println("✅ Connexion réussie: " + loginRequest.getUsername() + " - Role: " + user.getRole());

        return new LoginResponse(true, token, user.getUsername(), user.getRole());
    }

    private String generateToken() {
        return "token-" + UUID.randomUUID().toString() + "-" + System.currentTimeMillis();
    }

    // Méthode pour créer des utilisateurs par défaut
    public void createDefaultUsers() {
        // Admin
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@toyago.com");
            admin.setRole("ADMIN");
            admin.setActive(true);
            userRepository.save(admin);
            System.out.println("✅ Admin créé: admin / admin123");
        }

        // User
        if (!userRepository.existsByUsername("user")) {
            User user = new User();
            user.setUsername("user");
            user.setPassword("user123");
            user.setEmail("user@toyago.com");
            user.setRole("USER");
            user.setActive(true);
            userRepository.save(user);
            System.out.println("✅ Utilisateur créé: user / user123");
        }
    }
}
