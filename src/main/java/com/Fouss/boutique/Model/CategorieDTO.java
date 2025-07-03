package com.Fouss.boutique.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor

public class CategorieDTO {

    private Long id;
    private String nom;
    private String description;
    private String image;  //
    // Assuming you need an image field
    // Constructor that assigns values
    public CategorieDTO(Long id, String nom, String description, String image) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.image = image;  // Assign the image field
    }
}

