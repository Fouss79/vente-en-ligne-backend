package com.Fouss.boutique.Repository;


import com.Fouss.boutique.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProduitRepository extends JpaRepository<Produit,Long> {
    List<Produit> findByCategorieId(long categorieId);
    List<Produit> findByBoutiqueId(long boutiqueId);

}

