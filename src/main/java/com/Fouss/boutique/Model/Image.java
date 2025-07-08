package com.Fouss.boutique.Model;


import jakarta.persistence.*;

@Entity
@Table(name = "Image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String description; // optionnel

    private boolean visibleInCarousel; // utile pour filtrer uniquement les images du slider

    // --- Getters et Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isVisibleInCarousel() {
        return visibleInCarousel;
    }

    public void setVisibleInCarousel(boolean visibleInCarousel) {
        this.visibleInCarousel = visibleInCarousel;
    }
}
