package com.projet.monsitetoyago.entity;

import com.projet.monsitetoyago.entity.User;
import com.projet.monsitetoyago.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializerSimplified implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        createUserIfNotExists("admin", "Admin@Focus!2026", "ADMIN");
        createUserIfNotExists("user", "User@Focus!2026", "USER");
    }

    private void createUserIfNotExists(String username, String password, String role) {
        if (!userRepository.existsByUsername(username)) {
            User user = new User();
            user.setUsername(username);
            // Attention: sans hash, à utiliser uniquement en développement
            user.setPassword(password);
            user.setRole(role);
            user.setEmail(username + "@focus-supply.com");

            userRepository.save(user);

            System.out.println("✅ Utilisateur créé - " + username + " / " + password);
        }
    }
}
