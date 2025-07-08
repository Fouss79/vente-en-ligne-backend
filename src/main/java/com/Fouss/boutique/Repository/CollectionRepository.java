package com.Fouss.boutique.Repository;


import com.Fouss.boutique.Model.Collection;
import com.Fouss.boutique.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findByBoutiqueId(Long boutiqueId);
}