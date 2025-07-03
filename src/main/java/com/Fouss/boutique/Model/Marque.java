package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Data
@Table(name = "marque")
public class Marque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    @OneToMany(mappedBy = "marque", fetch = FetchType.LAZY)
    @JsonIgnore // ← pour ignorer le champ marque dans chaque produit
    private List<Produit> produits;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String image;  // Consider storing the URL rather than the image in base64
    // Produits.java
    @ManyToOne
    @JsonBackReference
    private CollectionMarque collection;

}


