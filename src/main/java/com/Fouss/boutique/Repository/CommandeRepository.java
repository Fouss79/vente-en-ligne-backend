package com.Fouss.boutique.Repository;

import com.Fouss.boutique.Model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findByClientId(Long utilisateurId);

    @Query("SELECT DISTINCT c FROM Commande c JOIN c.lignes l WHERE l.produit.boutique.utilisateur.id = :utilisateurId")
    List<Commande> findByLignesProduitBoutiqueUtilisateurId(@Param("utilisateurId") Long utilisateurId);
}