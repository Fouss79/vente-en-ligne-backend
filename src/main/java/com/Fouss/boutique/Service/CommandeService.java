package com.Fouss.boutique.Service;

import com.Fouss.boutique.Model.*;
import com.Fouss.boutique.Repository.CommandeRepository;
import com.Fouss.boutique.Repository.LigneCommandeRepository;
import com.Fouss.boutique.Repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final LigneCommandeRepository ligneCommandeRepository;
    private final ProduitRepository produitRepository;

    public CommandeService(CommandeRepository cr, LigneCommandeRepository lcr, ProduitRepository pr) {
        this.commandeRepository = cr;
        this.ligneCommandeRepository = lcr;
        this.produitRepository = pr;
    }

    public List<Commande> getToutesCommandes() {
        return commandeRepository.findAll();
    }
    public void supprimerCommande(Long commandeId) {
        commandeRepository.deleteById(commandeId);
    }
    public Commande passerCommande(Long utilisateurId, List<LigneCommandeDTO> ligneDtos) {
        Commande commande = new Commande();
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut(StatutCommande.EN_ATTENTE);


        Utilisateur client = new Utilisateur();
        client.setId(utilisateurId); // ou va chercher le client dans la base
        commande.setClient(client);

        List<LigneCommande> lignes = new ArrayList<>();
        double total = 0;

        for (LigneCommandeDTO dto : ligneDtos) {
            Produit produit = produitRepository.findById(dto.getProduitId()).orElseThrow();

            LigneCommande ligne = new LigneCommande();
            ligne.setProduit(produit);
            ligne.setQuantite(dto.getQuantite());
            ligne.setPrix(produit.getPrix() * dto.getQuantite());
            ligne.setCommande(commande);

            lignes.add(ligne);
            total += ligne.getPrix();
        }

        commande.setTotal(total);
        commande.setLignes(lignes);

        return commandeRepository.save(commande);
    }
    public List<Commande> getCommandesParVendeur(Long utilisateurId) {
        return commandeRepository.findByLignesProduitBoutiqueUtilisateurId(utilisateurId);
    }
    public Commande changerStatut(Long id, String nouveauStatut) {
        Commande commande = commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable"));

        try {
            StatutCommande statutEnum = StatutCommande.valueOf(nouveauStatut.toUpperCase());
            commande.setStatut(statutEnum);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Statut invalide : " + nouveauStatut);
        }

        return commandeRepository.save(commande);
    }



    public List<Commande> getCommandesClient(Long clientId) {
        return commandeRepository.findAll().stream()
                .filter(cmd -> cmd.getClient().getId().equals(clientId))
                .collect(Collectors.toList());
    }
}

