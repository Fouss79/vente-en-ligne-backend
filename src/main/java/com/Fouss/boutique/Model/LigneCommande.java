package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class LigneCommande {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Produit produit;

    private int quantite;

    private double prix;

    @ManyToOne
    @JsonIgnore
    private Commande commande;

    // getters/setters
}

