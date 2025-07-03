package com.Fouss.boutique.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
    private String role;
    private String image;
    private String RoleDemande;
    private String notification;


    // Constructeur
    public UtilisateurDTO(Long id, String nom, String prenom, String email, String telephone, String adresse, String role,String image) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.role = role;
        this.image=image;
    }

    // Getters et setters (ou utilise @Data de Lombok si tu préfères)
}

