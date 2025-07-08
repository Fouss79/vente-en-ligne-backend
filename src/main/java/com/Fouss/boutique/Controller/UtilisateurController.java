package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Mapper.UtilisateurMapper;
import com.Fouss.boutique.Model.Role;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Model.UtilisateurDTO;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import com.Fouss.boutique.Service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/utilisateur")
@CrossOrigin(origins = "http://localhost:3000")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    private
    // GET : Récupérer tous les utilisateur
    @GetMapping
    ResponseEntity<List<UtilisateurDTO>> getAllUsers() {
        List<Utilisateur> users = utilisateurService.getAllUtilisateur();

        List<UtilisateurDTO> dtos = users.stream()
                .map(u -> new UtilisateurDTO(
                        u.getId(),
                        u.getNom(),
                        u.getPrenom(),
                        u.getEmail(),
                        u.getTelephone(),
                        u.getAdresse(),
                        u.getRole().name(),
                        u.getImage()
                ))
                .toList();

        return ResponseEntity.ok(dtos);
    }



    // GET : Récupérer un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getClientById(@PathVariable Long id) {
        Optional<Utilisateur> user = utilisateurService.getUtilisateurById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping("/demandes-role")
    public List<UtilisateurDTO> getDemandesRole() {
        return utilisateurRepository.findAll().stream()
                .filter(u -> u.getRoleDemande() != null)
                .map(u -> {
                    UtilisateurDTO dto = new UtilisateurDTO();
                    dto.setId(u.getId());
                    dto.setNom(u.getNom());
                    dto.setEmail(u.getEmail());
                    dto.setRole(u.getRole().toString());
                    dto.setRoleDemande(u.getRoleDemande().toString());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    @PutMapping("/{id}/valider-role")
    public ResponseEntity<?> validerDemandeRole(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (utilisateur.getRoleDemande() != null) {
            utilisateur.setRole(utilisateur.getRoleDemande());
            utilisateur.setRoleDemande(null);
            utilisateur.setNotification("Votre demande de rôle a été acceptée !");
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok("Rôle validé !");
        } else {
            return ResponseEntity.badRequest().body("Aucune demande de rôle à valider.");
        }
    }
    @PutMapping("/{id}/clear-notification")
    public ResponseEntity<Void> clearNotification(@PathVariable Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        utilisateur.setNotification(null);
        utilisateurRepository.save(utilisateur);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/demande-role")
    public ResponseEntity<?> demanderRole(
            @PathVariable Long id,
            @RequestParam String roleSouhaite
    ) {
        Utilisateur utilisateur = utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        try {
            Role role = Role.valueOf(roleSouhaite.toUpperCase());
            utilisateur.setRoleDemande(role);
            utilisateurRepository.save(utilisateur);
            return ResponseEntity.ok("Demande de rôle enregistrée !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Rôle invalide !");
        }
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<?> updateRoleUtilisateur(
            @PathVariable("id") Long utilisateurId,
            @RequestParam("role") String roleStr) {
        try {
            utilisateurService.updateUserRole(utilisateurId, roleStr);
            return ResponseEntity.ok("Rôle mis à jour avec succès");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilisateurDTO> updateUtilisateur(
            @PathVariable Long id,
            @RequestParam("prenom") String prenom,
            @RequestParam("nom") String nom,
            @RequestParam("adresse") String adresse,
            @RequestParam("telephone") String telephone,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'id: " + id));

        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setAdresse(adresse);
        utilisateur.setTelephone(telephone);

        // Gérer l'image si elle est envoyée
        if (image != null && !image.isEmpty()) {
            String imagePath = saveImage(image);
            utilisateur.setImage(imagePath);
        }

        Utilisateur updatedUser = utilisateurRepository.save(utilisateur);

        // Création du DTO avec le constructeur vide + setters
        UtilisateurDTO dto = new UtilisateurDTO();
        dto.setId(updatedUser.getId());
        dto.setPrenom(updatedUser.getPrenom());
        dto.setNom(updatedUser.getNom());
        dto.setAdresse(updatedUser.getAdresse());
        dto.setTelephone(updatedUser.getTelephone());
        dto.setImage(updatedUser.getImage());

        return ResponseEntity.ok(dto);

    }
    private String saveImage(MultipartFile image) {
        try {
            String uploadDir = "uploads/";
            String fileName = image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'image", e);
        }
    }


    // POST : Créer un nouvel utilisateur

    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur) {
        Utilisateur created = utilisateurService.createUtilisateur(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @PostMapping("/create")
    public ResponseEntity<Utilisateur> createUtilisateur(
            @RequestParam String prenom,
            @RequestParam String nom,
            @RequestParam String email,
            @RequestParam String motDePasse,
            @RequestParam String telephone,
            @RequestParam String adresse,
            @RequestParam(required = false) MultipartFile image // facultatif si tu veux gérer l'image
    ) {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setPrenom(prenom);
        utilisateur.setNom(nom);
        utilisateur.setEmail(email);
        utilisateur.setMotDePasse(motDePasse);
        utilisateur.setTelephone(telephone);
        utilisateur.setAdresse(adresse);

        // Si tu veux gérer une image :
        if (image != null && !image.isEmpty()) {
            String nomImage = image.getOriginalFilename();
            utilisateur.setImage(nomImage);

            // Sauvegarder l'image dans un dossier local (ex: uploads/)
            try {
                Path imagePath = Paths.get("uploads/", nomImage);
                Files.write(imagePath, image.getBytes());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Utilisateur created = utilisateurService.createUtilisateur(utilisateur);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    // DELETE : Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateur(id);
        return ResponseEntity.noContent().build();
    }
}
