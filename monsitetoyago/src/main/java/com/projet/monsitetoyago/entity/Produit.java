package com.projet.monsitetoyago.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "produits")
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column()
    private String description;

    @Column(nullable = false)
    private Double prix;

    private String imageUrl;

    @Column(nullable = false)
    private String categorie;

    // Nouveaux champs pour les formations
    @Column()
    private String duree;

    @Column()
    private String type; // Formation, Coaching, Atelier, etc.

    @Column()
    private String niveau; // Débutant, Intermédiaire, Avancé, Expert

    @Column()
    private String prerequis;

    @Column()
    private String objectifs;

    @Column()
    private String programme;

    @Column(length = 500)
    private String publicCible;

    // Champs existants
    private Boolean enPromotion = false;
    private Double prixPromotion;
    private Boolean enStock = true;
    private Integer quantiteStock = 0;
    private LocalDateTime dateCreation = LocalDateTime.now();
    private LocalDateTime dateModification = LocalDateTime.now();

    // Constructeurs
    public Produit() {}

    // Getters et Setters pour les nouveaux champs
    public String getDuree() { return duree; }
    public void setDuree(String duree) { this.duree = duree; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getNiveau() { return niveau; }
    public void setNiveau(String niveau) { this.niveau = niveau; }

    public String getPrerequis() { return prerequis; }
    public void setPrerequis(String prerequis) { this.prerequis = prerequis; }

    public String getObjectifs() { return objectifs; }
    public void setObjectifs(String objectifs) { this.objectifs = objectifs; }

    public String getProgramme() { return programme; }
    public void setProgramme(String programme) { this.programme = programme; }

    public String getPublicCible() { return publicCible; }
    public void setPublicCible(String publicCible) { this.publicCible = publicCible; }

    // Getters et Setters existants...
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }

    public Boolean getEnPromotion() { return enPromotion; }
    public void setEnPromotion(Boolean enPromotion) { this.enPromotion = enPromotion; }

    public Double getPrixPromotion() { return prixPromotion; }
    public void setPrixPromotion(Double prixPromotion) { this.prixPromotion = prixPromotion; }

    public Boolean getEnStock() { return enStock; }
    public void setEnStock(Boolean enStock) { this.enStock = enStock; }

    public Integer getQuantiteStock() { return quantiteStock; }
    public void setQuantiteStock(Integer quantiteStock) { this.quantiteStock = quantiteStock; }

    public LocalDateTime getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDateTime dateCreation) { this.dateCreation = dateCreation; }

    public LocalDateTime getDateModification() { return dateModification; }
    public void setDateModification(LocalDateTime dateModification) { this.dateModification = dateModification; }

    @PreUpdate
    public void preUpdate() {
        this.dateModification = LocalDateTime.now();
    }
}
