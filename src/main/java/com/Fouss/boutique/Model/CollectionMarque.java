package com.Fouss.boutique.Model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class CollectionMarque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String titre;
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

}
