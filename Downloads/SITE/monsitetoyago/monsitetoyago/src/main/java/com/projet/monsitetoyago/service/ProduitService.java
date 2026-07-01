package com.projet.monsitetoyago.service;


import com.projet.monsitetoyago.entity.Produit;
import com.projet.monsitetoyago.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public List<Produit> getAllProduits() {
        return produitRepository.findAllByOrderByDateCreationDesc();
    }

    public Optional<Produit> getProduitById(Long id) {
        return produitRepository.findById(id);
    }

    public Produit createProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public Produit updateProduit(Long id, Produit produitDetails) {
        Optional<Produit> optionalProduit = produitRepository.findById(id);

        if (optionalProduit.isPresent()) {
            Produit produit = optionalProduit.get();

            produit.setNom(produitDetails.getNom());
            produit.setDescription(produitDetails.getDescription());
            produit.setPrix(produitDetails.getPrix());
            produit.setImageUrl(produitDetails.getImageUrl());
            produit.setCategorie(produitDetails.getCategorie());
            produit.setEnPromotion(produitDetails.getEnPromotion());
            produit.setPrixPromotion(produitDetails.getPrixPromotion());
            produit.setEnStock(produitDetails.getEnStock());
            produit.setQuantiteStock(produitDetails.getQuantiteStock());

            return produitRepository.save(produit);
        }

        return null;
    }

    public boolean deleteProduit(Long id) {
        if (produitRepository.existsById(id)) {
            produitRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Produit> getProduitsByCategorie(String categorie) {
        return produitRepository.findByCategorie(categorie);
    }

    public List<Produit> getProduitsEnPromotion() {
        return produitRepository.findByEnPromotionTrue();
    }

    public List<String> getCategories() {
        return produitRepository.findDistinctCategories();
    }

    public List<Produit> searchProduits(String searchTerm) {
        return produitRepository.findByNomContainingIgnoreCase(searchTerm);
    }
}
