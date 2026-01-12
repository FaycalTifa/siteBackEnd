package com.projet.monsitetoyago.controller;

import com.projet.monsitetoyago.entity.Produit;
import com.projet.monsitetoyago.service.FileStorageService;
import com.projet.monsitetoyago.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @Autowired
    private FileStorageService fileStorageService;

    @Value("${app.base-url}")
    private String baseUrl;

    // Modifiez la méthode createProduitWithImage pour utiliser l'URL complète
    @PostMapping
    public ResponseEntity<?> createProduitWithImage(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("prix") Double prix,
            @RequestParam(value = "categorie", defaultValue = "Général") String categorie,
            @RequestParam(value = "quantiteStock", defaultValue = "10") Integer quantiteStock,
            @RequestParam("image") MultipartFile imageFile) {

        try {
            System.out.println("=== NOUVEAU PRODUIT ===");
            System.out.println("📝 Nom: " + nom);
            System.out.println("📋 Description: " + description);

            // 1. Upload de l'image
            if (imageFile.isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of("error", "L'image est requise"));
            }

            String fileName = fileStorageService.storeFile(imageFile);
            // URL COMPLÈTE pour l'image
            String imageUrl = baseUrl + "/uploads/images/" + fileName;

            System.out.println("✅ Image sauvegardée: " + fileName);
            System.out.println("🔗 URL image complète: " + imageUrl);

            // 2. Création du produit
            Produit produit = new Produit();
            produit.setNom(nom);
            produit.setDescription(description);
            produit.setPrix(prix);
            produit.setCategorie(categorie);
            produit.setImageUrl(imageUrl); // URL COMPLÈTE ici
            produit.setQuantiteStock(quantiteStock);
            produit.setEnStock(quantiteStock > 0);
            produit.setEnPromotion(false);
            produit.setDateCreation(java.time.LocalDateTime.now());

            Produit savedProduit = produitService.createProduit(produit);

            System.out.println("💾 Produit enregistré en base, ID: " + savedProduit.getId());
            System.out.println("=========================");

            // 3. Réponse
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Produit créé avec succès");
            response.put("produit", savedProduit);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            System.err.println("❌ ERREUR lors de la création:");
            e.printStackTrace();

            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Erreur lors de la création: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errorResponse);
        }
    }

    // Modifiez getAllProduits pour s'assurer que toutes les images ont l'URL complète
    @GetMapping
    public ResponseEntity<?> getAllProduits() {
        try {
            List<Produit> produits = produitService.getAllProduits();

            // Assurez-vous que toutes les images ont l'URL complète
            List<Produit> produitsAvecUrlComplète = produits.stream()
                    .map(produit -> {
                        // Si l'URL ne contient pas le baseUrl, l'ajouter
                        if (produit.getImageUrl() != null && !produit.getImageUrl().startsWith("http")) {
                            // Si c'est un chemin relatif, ajouter le baseUrl
                            if (produit.getImageUrl().startsWith("/")) {
                                produit.setImageUrl(baseUrl + produit.getImageUrl());
                            } else {
                                produit.setImageUrl(baseUrl + "/uploads/images/" + produit.getImageUrl());
                            }
                        }
                        return produit;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(produitsAvecUrlComplète);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
