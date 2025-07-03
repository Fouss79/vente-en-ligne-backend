package com.Fouss.boutique.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
public class LigneCommandeDTO {
    private Long produitId;
    private int quantite;
    // Getters & setters
}
