package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Boutique {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
    private String logoUrl;

    @ManyToOne
    @JsonBackReference
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "boutique")
    @JsonIgnore // Pour éviter la récursion infinie
    private List<Produit> produits;

    @OneToMany(mappedBy = "boutique", cascade = CascadeType.ALL)
    @JsonIgnore // Empêche aussi la récursion sur les collections
    private List<Collection> collections;
}
