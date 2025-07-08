package com.Fouss.boutique.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitDTO {

    private Long id;
    private String nom;
    private Double prix;

    private String image;

    private String vendeurPrenom;
    private String vendeurNom;
    private String vendeurImage;
    private Integer stock;
    private Long categorieId;
    private  Long marqueId;
    private String categorieNom;
    private  String marqueNom;
    private Long boutiqueId;
    private String boutiqueNom;
    private LocalDateTime datecreation;
    private Double moyenne;



    // Ajout de l'ID de la catégorie (si nécessaire)

    // Vous pouvez également ajouter une version simplifiée de la catégorie si nécessaire
    // private CategorieDTO categorie; // Si vous souhaitez envoyer plus d'informations sur la catégorie
}
