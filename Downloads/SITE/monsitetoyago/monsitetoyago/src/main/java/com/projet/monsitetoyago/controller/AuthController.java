package com.projet.monsitetoyago.controller;


import com.projet.monsitetoyago.dto.LoginRequest;
import com.projet.monsitetoyago.dto.LoginResponse;
import com.projet.monsitetoyago.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        System.out.println("🔐 Tentative de connexion: " + loginRequest.getUsername());

        LoginResponse response = authService.authenticate(loginRequest);

        if (response.getSuccess()) {
            System.out.println("✅ Connexion réussie: " + loginRequest.getUsername());
            return ResponseEntity.ok(response);
        } else {
            System.out.println("❌ Échec connexion: " + loginRequest.getUsername() + " - " + response.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAuth() {
        return ResponseEntity.ok("✅ API Auth fonctionne - " + System.currentTimeMillis());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Déconnexion réussie");
    }
}
