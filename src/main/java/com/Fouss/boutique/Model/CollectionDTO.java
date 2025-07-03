package com.Fouss.boutique.Model;

import com.Fouss.boutique.Model.Produit;
import lombok.Data;

import java.util.List;

@Data
public class CollectionDTO {
    private Long id;
    private String nom;
    private String description;
    private String image;
    private List<Produit> produits; // On utilise un ProduitDTO simplifié
}

