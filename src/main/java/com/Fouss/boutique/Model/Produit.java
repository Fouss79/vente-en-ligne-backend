package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Entity
public class Produit {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String description;
    private Double prix;
    private Integer stock;
    private String image;

    @ManyToOne
    private Categorie categorie;
    @ManyToOne
    private Marque marque;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ImageProduit> images = new ArrayList<>();

    @ManyToOne
    private Boutique boutique;
    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateDeCreation;
    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Avis> avis;

    @Transient
    private Double moyenne;

    // Getters et setters
}
