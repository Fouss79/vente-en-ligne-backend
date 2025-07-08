package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.time.LocalDateTime;
@Data
@Entity
public class Avis {
    @Id
    @GeneratedValue
    private Long id;

    private int note; // de 1 à 5 étoiles
    private String commentaire;

    @ManyToOne
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnore
    private Produit produit;

    private LocalDateTime date = LocalDateTime.now();
}

