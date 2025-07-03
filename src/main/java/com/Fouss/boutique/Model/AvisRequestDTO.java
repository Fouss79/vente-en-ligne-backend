package com.Fouss.boutique.Model;

import lombok.Data;

@Data
public class AvisRequestDTO {
    private int note;
    private String commentaire;
    private Long utilisateurId;
}

