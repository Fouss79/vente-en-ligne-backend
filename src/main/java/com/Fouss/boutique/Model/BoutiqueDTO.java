package com.Fouss.boutique.Model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class BoutiqueDTO {
    private Long id;
    private String nom;
    private String description;
    private Long utilisateurId;
    private Long categorieId;
    private Long marqueId;
    private String image;
    private List<ProduitDTO> produits;
}
