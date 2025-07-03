package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.Commande;
import com.Fouss.boutique.Model.LigneCommandeDTO;
import com.Fouss.boutique.Service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commande")
public class CommandeController {

    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<Commande>> getToutesCommandes() {
        return ResponseEntity.ok(commandeService.getToutesCommandes());
    }
    @DeleteMapping("/{commandeId}")
    public ResponseEntity<Void> supprimerCommande(@PathVariable Long commandeId) {
        commandeService.supprimerCommande(commandeId);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/statut")
    public ResponseEntity<Commande> changerStatutCommande(@PathVariable Long id, @RequestParam String statut) {
        return ResponseEntity.ok(commandeService.changerStatut(id, statut));
    }

    @PostMapping("/{clientId}")
    public ResponseEntity<Commande> passerCommande(@PathVariable Long clientId, @RequestBody List<LigneCommandeDTO> produits) {
        return ResponseEntity.ok(commandeService.passerCommande(clientId, produits));
    }
    @GetMapping("/vendeur/{id}")
    public List<Commande> getCommandesParVendeur(@PathVariable Long id) {
        return commandeService.getCommandesParVendeur(id);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Commande>> getCommandes(@PathVariable Long clientId) {
        return ResponseEntity.ok(commandeService.getCommandesClient(clientId));
    }
}
