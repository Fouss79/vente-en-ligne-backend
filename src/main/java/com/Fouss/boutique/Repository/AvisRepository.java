package com.Fouss.boutique.Repository;

import com.Fouss.boutique.Model.Avis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {
    List<Avis> findByProduitId(Long produitId);
    @Query("SELECT AVG(a.note) FROM Avis a WHERE a.produit.id = :produitId")
    Double getMoyenneByProduitId(@Param("produitId") Long produitId);

}

