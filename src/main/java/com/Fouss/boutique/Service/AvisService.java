package com.Fouss.boutique.Service;

import com.Fouss.boutique.Model.Avis;
import com.Fouss.boutique.Model.Produit;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Repository.AvisRepository;
import com.Fouss.boutique.Repository.ProduitRepository;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvisService {
    @Autowired
    private AvisRepository avisRepository;
    @Autowired private ProduitRepository produitRepository;
    @Autowired private UtilisateurRepository utilisateurRepository;

    public Avis ajouterAvis(Long produitId, Long utilisateurId, Avis avis) {
        Produit produit = produitRepository.findById(produitId).orElseThrow();
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId).orElseThrow();

        avis.setProduit(produit);
        avis.setUtilisateur(utilisateur);
        return avisRepository.save(avis);
    }

    public List<Avis> getAvisParProduit(Long produitId) {
        return avisRepository.findByProduitId(produitId);
    }


}

