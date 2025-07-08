package com.Fouss.boutique.Repository;
import com.Fouss.boutique.Model.Categorie;
import com.Fouss.boutique.Model.Marque;
import com.Fouss.boutique.Model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
}
