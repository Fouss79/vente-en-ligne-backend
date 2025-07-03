package com.Fouss.boutique.Repository;

import com.Fouss.boutique.Model.ImageProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageProduitRepository extends JpaRepository<ImageProduit, Long> {
    List<ImageProduit> findByProduitId(Long produitId);
}

