package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.*;
import com.Fouss.boutique.Repository.BoutiqueRepository;
import com.Fouss.boutique.Repository.CollectionRepository;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    @PostMapping("/boutique")
    public ResponseEntity<Collection> creerCollection(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("boutiqueId") Long boutiqueId,
            @RequestParam(value = "image", required = false) MultipartFile imageFile
    ) {
        Boutique boutique = boutiqueRepository.findById(boutiqueId)
                .orElseThrow(() -> new RuntimeException("Boutique introuvable"));

        String imagePath = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String filename = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path path = Paths.get("uploads/" + filename);
                Files.copy(imageFile.getInputStream(), path);
                imagePath = path.toString();
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        Collection collection = new Collection();
        collection.setNom(nom);
        collection.setDescription(description);
        collection.setBoutique(boutique);
        collection.setImage(imagePath);

        Collection saved = collectionRepository.save(collection);
        return ResponseEntity.ok(saved);
    }




    @GetMapping("/boutique/{id}")
    public List<CollectionDTO> getCollectionsWithProduits(@PathVariable Long id) {
        List<Collection> collections = collectionRepository.findByBoutiqueId(id);
        return collections.stream().map(collection -> {
            CollectionDTO dto = new CollectionDTO();
            dto.setId(collection.getId());
            dto.setNom(collection.getNom());
            dto.setDescription(collection.getDescription());
            dto.setImage(collection.getImage());
            dto.setProduits(collection.getProduits()); // Assure-toi que les produits sont bien chargés
            return dto;
        }).collect(Collectors.toList());
    }


    @GetMapping("/{id}/produits")
    public ResponseEntity<List<Produit>> getProduitsDeCollection(@PathVariable Long id) {
        Optional<Collection> collection = collectionRepository.findById(id);
        return collection.map(value -> ResponseEntity.ok(value.getProduits()))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        return ResponseEntity.of(collectionRepository.findById(id));
    }
}
