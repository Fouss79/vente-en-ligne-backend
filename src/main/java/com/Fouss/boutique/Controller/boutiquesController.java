package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.Boutique;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Repository.BoutiqueRepository;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@RestController
@RequestMapping("/api/boutiques")
@RequiredArgsConstructor
public class boutiquesController {

    private final BoutiqueRepository boutiqueRepository;
    private final UtilisateurRepository utilisateurRepository;

    @PostMapping
    public ResponseEntity<?> creerBoutique(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam(value = "logo", required = false) MultipartFile logo,
            Principal principal
    ) {
        try {
            // Récupération de l'utilisateur connecté
            String email = principal.getName();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Création de la boutique
            Boutique boutique = new Boutique();
            boutique.setNom(nom);
            boutique.setDescription(description);
            boutique.setUtilisateur(utilisateur);

            // Gestion du logo
            if (logo != null && !logo.isEmpty()) {
                String fileName = UUID.randomUUID() + "_" + logo.getOriginalFilename();
                Path filePath = Paths.get("uploads/logos/" + fileName);
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, logo.getBytes());
                boutique.setLogoUrl("/uploads/logos/" + fileName);
            }

            Boutique savedBoutique = boutiqueRepository.save(boutique);
            return ResponseEntity.ok(savedBoutique);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de la boutique : " + e.getMessage());
        }
    }
}

