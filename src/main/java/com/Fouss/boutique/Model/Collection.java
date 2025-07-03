package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;
    private String image;

    @ManyToOne
    @JoinColumn(name = "boutique_id")
    private Boutique boutique;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Produit> produits;


}
