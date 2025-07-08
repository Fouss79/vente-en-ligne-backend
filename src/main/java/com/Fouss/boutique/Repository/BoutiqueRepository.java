package com.Fouss.boutique.Repository;

import com.Fouss.boutique.Model.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoutiqueRepository extends JpaRepository<Boutique, Long> {
    List<Boutique> findByUtilisateurId(Long utilisateurId);

}

