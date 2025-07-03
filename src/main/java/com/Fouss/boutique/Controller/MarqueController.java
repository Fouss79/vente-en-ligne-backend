package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.Marque;
import com.Fouss.boutique.Model.MarqueDTO;
import com.Fouss.boutique.Service.MarqueService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/marque")
@CrossOrigin(origins = "http://localhost:3000")
public class MarqueController {

    @Autowired
    private MarqueService marqueService;

    // Récupérer la liste des catégories
    @GetMapping
    public ResponseEntity<List<MarqueDTO>> getAllMarque() {
        List<Marque> marquesList = marqueService.getAllMarque();

        // Convertir les catégories en DTOs
        List<MarqueDTO> marqueDTOList = marquesList.stream()
                .map(marque -> new MarqueDTO(
                        marque.getId(),
                        marque.getNom(),
                        marque.getDescription(),
                        marque.getImage()


                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(marqueDTOList);
    }

    // Récupérer une catégorie par ID


    // Créer une nouvelle catégorie
    @PostMapping
    public ResponseEntity<MarqueDTO> createMarque(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        // Sauvegarder l'image et récupérer son chemin
        String imagePath = saveImage(image);

        // Créer la catégorie
        Marque  marque = new Marque();
        marque.setNom(nom);
        marque.setDescription(description);
        marque.setImage(imagePath);


        // Sauvegarder la catégorie
        Marque createdMarque = marqueService.creerMarque(marque);

        // Conversion de l'entité en DTO avant de renvoyer
        MarqueDTO marqueDTO = new MarqueDTO(
                createdMarque.getId(),
                createdMarque.getNom(),
                createdMarque.getDescription(),
                createdMarque.getImage()

        );

        return ResponseEntity.status(HttpStatus.CREATED).body(marqueDTO);
    }

    // Méthode pour sauvegarder l'image sur le serveur
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

    // Mettre à jour une catégorie
    @PutMapping("/{id}")
    public ResponseEntity<MarqueDTO> updateMarque(
            @PathVariable Long id,
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        Marque marque = new Marque();

        // Sauvegarder l'image si nécessaire
        String imagePath = saveImage(image);
        marque.setNom(nom);
        marque.setDescription(description);
        marque.setImage(imagePath);

        // Mettre à jour la catégorie
        Marque updatedMarque = marqueService.updateMarque(id, marque);

        // Conversion en DTO avant de renvoyer
        MarqueDTO marqueDTO = new MarqueDTO(
                updatedMarque.getId(),
                updatedMarque.getNom(),
                updatedMarque.getDescription(),
                updatedMarque.getImage()
        );

        return ResponseEntity.ok(marqueDTO);
    }

    // Supprimer une catégorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarque(@PathVariable Long id) {
        marqueService.deleteMarque(id);
        return ResponseEntity.noContent().build();
    }
}

