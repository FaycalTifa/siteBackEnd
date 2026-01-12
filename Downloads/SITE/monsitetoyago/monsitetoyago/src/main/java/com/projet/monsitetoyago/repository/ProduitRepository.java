package com.projet.monsitetoyago.repository;

import com.projet.monsitetoyago.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    List<Produit> findByCategorie(String categorie);

    List<Produit> findByEnPromotionTrue();

    List<Produit> findByEnStockTrue();

    List<Produit> findByNomContainingIgnoreCase(String nom);

    @Query("SELECT DISTINCT p.categorie FROM Produit p")
    List<String> findDistinctCategories();

    List<Produit> findAllByOrderByDateCreationDesc();
}
