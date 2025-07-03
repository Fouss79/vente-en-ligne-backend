package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.*;
import com.Fouss.boutique.Repository.*;
import com.Fouss.boutique.Service.CategorieService;
import com.Fouss.boutique.Service.ProduitService;
import com.Fouss.boutique.Service.UtilisateurService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    @Autowired
    private ProduitService produitService;
    @Autowired
    private CategorieService categorieService;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
     private BoutiqueRepository boutiqueRepository;
    @Autowired
     private CategorieRepository categorieRepository;
    @Autowired
     private MarqueRepository marqueRepository;
    @Autowired
    private ImageProduitRepository imageProduitRepository;
    @Autowired
    private CollectionRepository collectionRepository;

    // Dans ProduitController.java
    @GetMapping("/categorie/{categorieId}")
    public ResponseEntity<List<ProduitDTO>> getProduitsByCategorieId(@PathVariable Long categorieId) {
        List<Produit> produits = produitRepository.findByCategorieId(categorieId);

        List<ProduitDTO> produitDTOs = produits.stream().map(produit -> {
            ProduitDTO dto = new ProduitDTO();
            dto.setId(produit.getId());
            dto.setNom(produit.getNom());
            dto.setPrix(produit.getPrix());
            dto.setImage(produit.getImage());
            dto.setStock(produit.getStock());
            Double moyenne = produit.getAvis().stream()
                    .mapToInt(Avis::getNote)
                    .average()
                    .orElse(0.0);
            dto.setMoyenne(moyenne);

            if (produit.getBoutique() != null && produit.getBoutique().getUtilisateur() != null) {
                dto.setVendeurPrenom(produit.getBoutique().getUtilisateur().getPrenom());
                dto.setVendeurNom(produit.getBoutique().getUtilisateur().getNom());
                dto.setVendeurImage(produit.getBoutique().getUtilisateur().getImage());
            }

            if (produit.getCategorie() != null) {
                dto.setCategorieId(produit.getCategorie().getId());
                dto.setCategorieNom(produit.getCategorie().getNom());
            }

            if (produit.getMarque() != null) {
                dto.setMarqueId(produit.getMarque().getId());
                dto.setMarqueNom(produit.getMarque().getNom());
            }
            if (produit.getBoutique() != null) {
                dto.setBoutiqueId(produit.getBoutique().getId());
                dto.setBoutiqueNom(produit.getBoutique().getNom());
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(produitDTOs);
    }

    @GetMapping("/vendeur/{userId}")
    public ResponseEntity<List<ProduitDTO>> getProduitsByVendeur(@PathVariable Long userId) {
        List<Boutique> boutiques = boutiqueRepository.findByUtilisateurId(userId);

        List<Produit> produits = boutiques.stream()
                .flatMap(b -> produitRepository.findByBoutiqueId(b.getId()).stream())
                .toList();

        List<ProduitDTO> produitDTOs = produits.stream().map(produit -> {
            ProduitDTO dto = new ProduitDTO();
            dto.setId(produit.getId());
            dto.setNom(produit.getNom());
            dto.setPrix(produit.getPrix());
            dto.setImage(produit.getImage());
            dto.setStock(produit.getStock());
            Double moyenne = produit.getAvis().stream()
                    .mapToInt(Avis::getNote)
                    .average()
                    .orElse(0.0);
            dto.setMoyenne(moyenne);


            if (produit.getBoutique() != null && produit.getBoutique().getUtilisateur() != null) {
                dto.setVendeurPrenom(produit.getBoutique().getUtilisateur().getPrenom());
                dto.setVendeurNom(produit.getBoutique().getUtilisateur().getNom());
                dto.setVendeurImage(produit.getBoutique().getUtilisateur().getImage());
            }

            if (produit.getCategorie() != null) {
                dto.setCategorieId(produit.getCategorie().getId());
                dto.setCategorieNom(produit.getCategorie().getNom());
            }

            if (produit.getMarque() != null) {
                dto.setMarqueId(produit.getMarque().getId());
                dto.setMarqueNom(produit.getMarque().getNom());
            }

            dto.setBoutiqueId(produit.getBoutique().getId());
            dto.setBoutiqueNom(produit.getBoutique().getNom());

            return dto;
        }).toList();

        return ResponseEntity.ok(produitDTOs);
    }

    @GetMapping("/boutique/{boutiqueId}")
    public ResponseEntity<List<ProduitDTO>> getProduitsByBQoutiqId(@PathVariable Long boutiqueId)
    {
        List<Produit> produits = produitRepository.findByBoutiqueId(boutiqueId);

        List<ProduitDTO> produitDTOs = produits.stream().map(produit -> {
            ProduitDTO dto = new ProduitDTO();
            dto.setId(produit.getId());
            dto.setNom(produit.getNom());
            dto.setPrix(produit.getPrix());
            dto.setImage(produit.getImage());
            dto.setStock(produit.getStock());
            Double moyenne = produit.getAvis().stream()
                    .mapToInt(Avis::getNote)
                    .average()
                    .orElse(0.0);
            dto.setMoyenne(moyenne);


            if (produit.getBoutique() != null && produit.getBoutique().getUtilisateur() != null) {
                dto.setVendeurPrenom(produit.getBoutique().getUtilisateur().getPrenom());
                dto.setVendeurNom(produit.getBoutique().getUtilisateur().getNom());
                dto.setVendeurImage(produit.getBoutique().getUtilisateur().getImage());
            }

            if (produit.getCategorie() != null) {
                dto.setCategorieId(produit.getCategorie().getId());
                dto.setCategorieNom(produit.getCategorie().getNom());
            }

            if (produit.getMarque() != null) {
                dto.setMarqueId(produit.getMarque().getId());
                dto.setMarqueNom(produit.getMarque().getNom());
            }
            if (produit.getBoutique() != null) {
                dto.setBoutiqueId(produit.getBoutique().getId());
                dto.setBoutiqueNom(produit.getBoutique().getNom());
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(produitDTOs);
    }





    // Créer un produit

        private final String UPLOAD_DIR = "uploads/produits/";

    @PostMapping("/{boutiqueId}")
    public ResponseEntity<?> creerProduit(
            @RequestParam String nom,
            @RequestParam Double prix,
            @RequestParam Integer stock,
            @RequestParam Long categorieId,
            @RequestParam(required = false) Long marqueId,
            @PathVariable Long boutiqueId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            // Sauvegarder l'image sur le serveur
            String imagePath = saveImage(image);

            // Récupérer les entités liées
            Categorie categorie = categorieRepository.findById(categorieId)
                    .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
            Boutique boutique = boutiqueRepository.findById(boutiqueId)
                    .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));

            // Créer et sauvegarder le produit
            Produit produit = new Produit();
            produit.setNom(nom);
            produit.setPrix(prix);
            produit.setStock(stock);
            produit.setImage(imagePath);
            produit.setCategorie(categorie);
            produit.setBoutique(boutique);
            produit.setDateDeCreation(LocalDateTime.now());

            if (marqueId != null) {
                Marque marque = marqueRepository.findById(marqueId)
                        .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
                produit.setMarque(marque);
            }

            Produit savedProduit = produitRepository.save(produit);

            return ResponseEntity.ok(savedProduit);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    private String saveImage(MultipartFile image) {
        try {
            String uploadDir = "uploads/";
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

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

    @GetMapping
    public ResponseEntity<List<ProduitDTO>> getAllProduits() {
        List<Produit> produits = produitRepository.findAll();

        List<ProduitDTO> produitDTOs = produits.stream().map(produit -> {
            ProduitDTO dto = new ProduitDTO();
            dto.setId(produit.getId());
            dto.setNom(produit.getNom());
            dto.setPrix(produit.getPrix());
            dto.setImage(produit.getImage());
            dto.setStock(produit.getStock());
            dto.setDatecreation(produit.getDateDeCreation());
            Double moyenne = produit.getAvis().stream()
                    .mapToInt(Avis::getNote)
                    .average()
                    .orElse(0.0);
            dto.setMoyenne(moyenne);

            if (produit.getBoutique().getUtilisateur() != null) {
                dto.setVendeurPrenom(produit.getBoutique().getUtilisateur().getPrenom());
                dto.setVendeurNom(produit.getBoutique().getUtilisateur().getNom());
                dto.setVendeurImage(produit.getBoutique().getUtilisateur().getImage());
            }

            if (produit.getCategorie() != null) {
                dto.setCategorieId(produit.getCategorie().getId());
                dto.setCategorieNom(produit.getCategorie().getNom());
            }

            if (produit.getMarque() != null) {
                dto.setMarqueId(produit.getMarque().getId());
                dto.setMarqueNom(produit.getMarque().getNom());
            }
            if(produit.getBoutique()!=null){

                dto.setBoutiqueId(produit.getBoutique().getId());
                dto.setBoutiqueNom(produit.getBoutique().getNom());
            }

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(produitDTOs);
    }

    @PutMapping("/{produitId}/collection/{collectionId}")
    public ResponseEntity<?> assignerProduitACollection(
            @PathVariable Long produitId,
            @PathVariable Long collectionId) {

        Optional<Produit> produitOpt = produitRepository.findById(produitId);
        Optional<Collection> collectionOpt = collectionRepository.findById(collectionId);

        if (produitOpt.isEmpty() || collectionOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Produit produit = produitOpt.get();
        produit.setCollection(collectionOpt.get());
        produitRepository.save(produit);

        return ResponseEntity.ok("Produit associé à la collection avec succès");
    }


    // Obtenir tous les produits
    @GetMapping("/{id}")
    public ResponseEntity<ProduitDTO> getProduitById(@PathVariable Long id) {
        Produit produit = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        ProduitDTO produitDTO = new ProduitDTO();
        produitDTO.setId(produit.getId());
        produitDTO.setNom(produit.getNom());
        produitDTO.setPrix(produit.getPrix());
        produitDTO.setImage(produit.getImage());
        produitDTO.setStock(produit.getStock());
        Double moyenne = produit.getAvis().stream()
                .mapToInt(Avis::getNote)
                .average()
                .orElse(0.0);
        produitDTO.setMoyenne(moyenne);
        // Vendeur
        if (produit.getBoutique().getUtilisateur() != null) {
            produitDTO.setVendeurPrenom(produit.getBoutique().getUtilisateur().getPrenom());
            produitDTO.setVendeurNom(produit.getBoutique().getUtilisateur().getNom());
            produitDTO.setVendeurImage(produit.getBoutique().getUtilisateur().getImage());
        }

        // Catégorie
        if (produit.getCategorie() != null) {
            produitDTO.setCategorieId(produit.getCategorie().getId());
            produitDTO.setCategorieNom(produit.getCategorie().getNom());
        }

        // Marque
        if (produit.getMarque() != null) {
            produitDTO.setMarqueId(produit.getMarque().getId());
            produitDTO.setMarqueNom(produit.getMarque().getNom());
        }
        if(produit.getBoutique()!=null){

            produitDTO.setBoutiqueId(produit.getBoutique().getId());
            produitDTO.setBoutiqueNom(produit.getBoutique().getNom());
        }



        return ResponseEntity.ok(produitDTO);
    }
    @GetMapping("/api/produits/{id}/images")
    public List<ImageProduit> getImages(@PathVariable Long id) {
        Produit p = produitRepository.findById(id).orElseThrow();
        return p.getImages();
    }

    @PostMapping("/{produitId}/images")
    public ResponseEntity<?> uploadImages(
            @PathVariable Long produitId,
            @RequestParam("files") MultipartFile[] files,
            @RequestParam(value = "description", required = false) String description) {

        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

        List<ImageProduit> images = new ArrayList<>();

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String originalFilename = file.getOriginalFilename();
                String filename = System.currentTimeMillis() + "_" + originalFilename;
                Path uploadPath = Paths.get("uploads", filename);

                try {
                    Files.copy(file.getInputStream(), uploadPath);

                    ImageProduit img = new ImageProduit();
                    img.setProduit(produit);
                    img.setFilePath(uploadPath.toString());
                    img.setUrl("/uploads/" + filename);
                    img.setDescription(description);
                    images.add(img);
                } catch (IOException e) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body("Échec d'upload du fichier : " + originalFilename);
                }
            }
        }

        imageProduitRepository.saveAll(images);

        return ResponseEntity.ok("Images ajoutées avec succès !");
    }



    @GetMapping("/{id}/images")
    public List<ImageProduitDTO> getImagesByProduit(@PathVariable Long id) {
        Produit p = produitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
        return p.getImages().stream()
                .map(img -> new ImageProduitDTO(img.getId(), img.getUrl().replace("\\", "/"), img.getDescription(), img.getFilename()))
                .collect(Collectors.toList());
    }

    // Mettre à jour un produit
    ///@PutMapping("/{id}")
    //public ResponseEntity<ProduitDTO> updateProduit(
    //      @PathVariable Long id,
    //    @RequestParam("nom") String nom,
    //  @RequestParam("prix") Double prix,
    // @RequestParam("categorieId") Long categorieId,
    //@RequestParam("image") MultipartFile image) {

    //Categorie categorie = categorieService.getCategorieById(categorieId);
    //if (categorie == null) {
    //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    //}

    //String imagePath = saveImage(image);
    //Produits produits = produitsService.getProduitsById(id);
    //produits.setNom(nom);
    //produits.setPrix(prix);
    //produits.setCategorie(categorie);
    //produits.setImage(imagePath);

    //Produits updatedProduit = produitsService.updateProduits(produits);

    // Conversion en DTO avant de renvoyer
    //ProduitDTO produitDTO = new ProduitDTO(
    //      updatedProduit.getId(),
    //    updatedProduit.getNom(),
    //  updatedProduit.getPrix(),

    //updatedProduit.getImage(),
    //updatedProduit.getCategorie().getId()
    //);

    //return ResponseEntity.ok(produitDTO);
    //}

    // Supprimer un produit
    //@DeleteMapping("/{id}")
    // public ResponseEntity<Void> deleteProduit(@PathVariable Long id) {
    //   produitsService.deleteProduits(id);
    // return ResponseEntity.noContent().build();
    //}
}

