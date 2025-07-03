package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.Avis;
import com.Fouss.boutique.Model.AvisRequestDTO;
import com.Fouss.boutique.Model.Produit;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Repository.AvisRepository;
import com.Fouss.boutique.Repository.ProduitRepository;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import com.Fouss.boutique.Service.AvisService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/avis")
@RequiredArgsConstructor
public class AvisController {

    private final AvisRepository avisRepository;
    private final ProduitRepository produitRepository;
    private final UtilisateurRepository utilisateurRepository;
    @Autowired
    private AvisService avisService;


    @GetMapping("/produit/{produitId}")
    public List<Avis> getAvisParProduit(@PathVariable Long produitId) {
        return avisRepository.findByProduitId(produitId);
    }

    @PostMapping("/produit/{produitId}")
    public ResponseEntity<String> ajouterAvis(
            @PathVariable Long produitId,
            @RequestBody AvisRequestDTO avisDTO) {

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        Utilisateur utilisateur = utilisateurRepository.findById(avisDTO.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Avis avis = new Avis();
        avis.setNote(avisDTO.getNote());
        avis.setCommentaire(avisDTO.getCommentaire());
        avis.setProduit(produit);
        avis.setUtilisateur(utilisateur);

        avisRepository.save(avis);

        return ResponseEntity.ok("Avis ajouté avec succès !");
    }
    @GetMapping("/produit/{produitId}/moyenne")
    public ResponseEntity<Double> getMoyenneNote(@PathVariable Long produitId) {
        Double moyenne = avisRepository.getMoyenneByProduitId(produitId);
        return ResponseEntity.ok(moyenne != null ? moyenne : 0.0);
    }



}
