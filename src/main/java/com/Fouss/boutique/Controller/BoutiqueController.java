package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.*;
import com.Fouss.boutique.Repository.BoutiqueRepository;
import com.Fouss.boutique.Service.BoutiqueService;
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
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boutique")
@CrossOrigin(origins = "http://localhost:3000")
public class BoutiqueController {


    @Autowired
    BoutiqueRepository boutiqueRepository;
    @Autowired
    private BoutiqueService boutiqueService;
    @Autowired
    private UtilisateurService utilisateurService;
    @GetMapping
    public ResponseEntity<List<BoutiqueDTO>> getAllBoutiques() {

        List<Boutique>  boutiqueList = boutiqueService.getAllBoutiques();

        List<BoutiqueDTO> BoutiqDTOList= boutiqueList.stream().map(boutique -> {
            BoutiqueDTO dto = new BoutiqueDTO();
            dto.setId(boutique.getId());
            dto.setNom(boutique.getNom());
            dto.setDescription(boutique.getDescription());
            dto.setImage(boutique.getLogoUrl());
            dto.setUtilisateurId(boutique.getUtilisateur().getId());
            return dto;
        })
                .collect(Collectors.toList());

        return  ResponseEntity.ok(BoutiqDTOList);

    }

    @GetMapping("/{id}")
    public ResponseEntity<BoutiqueDTO> getBoutiqById(@PathVariable Long id) {
        Boutique btque = boutiqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvé"));

        BoutiqueDTO boutiqueDTO = new BoutiqueDTO();
        boutiqueDTO.setId(btque.getId());
        boutiqueDTO.setNom(btque.getNom());
        boutiqueDTO.setDescription(btque.getDescription());
        boutiqueDTO.setUtilisateurId(btque.getUtilisateur().getId());
        boutiqueDTO.setImage(btque.getLogoUrl());
        List<ProduitDTO> produits = btque.getProduits().stream().map(p -> {
            ProduitDTO produitDTO = new ProduitDTO();
            produitDTO.setId(p.getId());
            produitDTO.setNom(p.getNom());
            produitDTO.setPrix(p.getPrix());
            produitDTO.setImage(p.getImage());
            return produitDTO;
        }).collect(Collectors.toList());

        boutiqueDTO.setProduits(produits);



        return ResponseEntity.ok(boutiqueDTO);
    }


    @PostMapping
    public ResponseEntity<BoutiqueDTO> createBoutiqueWithParams(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("logoUrl") MultipartFile logoUrl,
            @RequestParam("utilisateurId") Long utilisateurId
    ) {
        Utilisateur utilisateur = utilisateurService.getUtilisateurById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        String imagePath = saveImage(logoUrl);
        Boutique boutique = new Boutique();
        boutique.setNom(nom);
        boutique.setDescription(description);
        boutique.setLogoUrl(imagePath);
        boutique.setUtilisateur(utilisateur);

        Boutique created = boutiqueService.createBoutique(boutique);

        BoutiqueDTO btq = new BoutiqueDTO();
        btq.setId(created.getId());
        btq.setNom(created.getNom());
        btq.setDescription(created.getDescription());
        btq.setUtilisateurId(created.getUtilisateur().getId());
        btq.setImage(created.getLogoUrl());


        return ResponseEntity.status(HttpStatus.CREATED).body(btq);

    }

    @PostMapping("/exemple")
    public String exemple(Principal principal) {
        String email = principal.getName(); // généralement l'email ou le username
        return "Bonjour " + email;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boutique> updateBoutique(@PathVariable Long id, @RequestBody Boutique boutique) {
        Boutique updated = boutiqueService.updateBoutique(id, boutique);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoutique(@PathVariable Long id) {
        boutiqueService.deleteBoutique(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/utilisateur/{utilisateurId}")
    public ResponseEntity<List<BoutiqueDTO>> getBoutiquesAvecProduits(@PathVariable Long utilisateurId) {
        List<Boutique> boutiques = boutiqueRepository.findByUtilisateurId(utilisateurId);

        List<BoutiqueDTO> dtoList = boutiques.stream().map(boutique -> {
            BoutiqueDTO dto = new BoutiqueDTO();
            dto.setId(boutique.getId());
            dto.setNom(boutique.getNom());
            dto.setDescription(boutique.getDescription());
            dto.setImage(boutique.getLogoUrl());
            dto.setUtilisateurId(boutique.getUtilisateur().getId());

            List<ProduitDTO> produits = boutique.getProduits().stream().map(p -> {
                ProduitDTO produitDTO = new ProduitDTO();
                produitDTO.setId(p.getId());
                produitDTO.setNom(p.getNom());
                produitDTO.setPrix(p.getPrix());
                produitDTO.setImage(p.getImage());
                return produitDTO;
            }).collect(Collectors.toList());

            dto.setProduits(produits);
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(dtoList);
    }


    private String saveImage(MultipartFile image) {
        try {
            // Chemin où vous souhaitez sauvegarder les images
            String uploadDir = "uploads/";
            String fileName = image.getOriginalFilename();
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Retournez le chemin où l'image est sauvegardée
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la sauvegarde de l'image", e);
        }

    }}