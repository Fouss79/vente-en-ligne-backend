package com.Fouss.boutique.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Utilisateur {
    @Id
    @GeneratedValue
    private Long id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String adresse;
    private String telephone;
    private  String image;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private Role roleDemande; // Par exemple : VENDEUR, ADMIN
    private String notification;



    public Utilisateur() {
        this.role = role.CLIENT;
    }

    @OneToMany(mappedBy = "utilisateur")
    @JsonManagedReference
    private List<Boutique> boutiques;

    // Getters et setters
}

