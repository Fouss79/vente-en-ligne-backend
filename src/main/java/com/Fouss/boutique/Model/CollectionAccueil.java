package com.Fouss.boutique.Model;

import jakarta.persistence.*;

@Entity
public class CollectionAccueil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Collection collection;

    public CollectionAccueil() {}

    public CollectionAccueil(Collection collection) {
        this.collection = collection;
    }

    public Long getId() {
        return id;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }
}

