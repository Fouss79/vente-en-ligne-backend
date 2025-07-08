package com.Fouss.boutique.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageProduitDTO {
    private Long id;
    private String url;
    private String description;
    private String filename;
}
