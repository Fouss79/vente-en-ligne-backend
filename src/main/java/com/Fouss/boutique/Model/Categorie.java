package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;
@Data
@Entity
public class Categorie {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
    private String image;
    @OneToMany(mappedBy = "categorie")
    @JsonIgnore // Empêche la récursion JSON
    private List<Produit> produits;



    // Getters et setters
}
