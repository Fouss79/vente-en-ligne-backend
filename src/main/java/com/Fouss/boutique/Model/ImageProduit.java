package com.Fouss.boutique.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.nio.file.Path;
import java.nio.file.Paths;

@Data
@Entity
@Table(name = "image_produit")
public class ImageProduit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "description")
    private String description;

    public String getFilename() {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    // Getter personnalisé pour corriger les backslashes dans l'URL
    public String getUrl() {
        if (url != null) {
            return url.replace("\\", "/");
        }
        return url;
    }
}
