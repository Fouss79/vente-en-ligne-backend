package com.Fouss.boutique.Service;

import com.Fouss.boutique.Model.Boutique;
import com.Fouss.boutique.Model.BoutiqueDTO;
import com.Fouss.boutique.Model.ProduitDTO;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Repository.BoutiqueRepository;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BoutiqueService {

    @Autowired
    private BoutiqueRepository boutiqueRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Boutique> getAllBoutiques() {
        return boutiqueRepository.findAll();
    }


    public BoutiqueDTO convertToDTO(Boutique boutique) {
        BoutiqueDTO dto = new BoutiqueDTO();
        dto.setId(boutique.getId());
        dto.setNom(boutique.getNom());
        dto.setDescription(boutique.getDescription());
        dto.setImage(boutique.getLogoUrl());
        dto.setUtilisateurId(boutique.getUtilisateur().getId());

        List<ProduitDTO> produitsDTO = boutique.getProduits().stream().map(produit -> {
            ProduitDTO pDto = new ProduitDTO();
            pDto.setId(produit.getId());
            pDto.setNom(produit.getNom());
            pDto.setPrix(produit.getPrix());
            pDto.setImage(produit.getImage());
            return pDto;
        }).collect(Collectors.toList());

        dto.setProduits(produitsDTO);
        return dto;
    }

    public Boutique createBoutique(Boutique boutique) {
        return boutiqueRepository.save(boutique);
    }

    public Boutique updateBoutique(Long id, Boutique newBoutique) {
        return boutiqueRepository.findById(id).map(b -> {
            b.setNom(newBoutique.getNom());
            b.setDescription(newBoutique.getDescription());
            b.setLogoUrl(newBoutique.getLogoUrl());
            b.setUtilisateur(newBoutique.getUtilisateur());
            return boutiqueRepository.save(b);
        }).orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
    }

    public Boutique creerBoutiqueDepuisEmail(String email, Boutique boutique) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email).orElseThrow();
        boutique.setUtilisateur(utilisateur);
        return boutiqueRepository.save(boutique);
    }


    public void deleteBoutique(Long id) {
        boutiqueRepository.deleteById(id);
    }

    public List<Boutique> getBoutiquesByUtilisateurId(Long utilisateurId) {
        return boutiqueRepository.findByUtilisateurId(utilisateurId);
    }
}
