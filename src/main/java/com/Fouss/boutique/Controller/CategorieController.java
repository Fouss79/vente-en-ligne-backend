package com.Fouss.boutique.Controller;



import com.Fouss.boutique.Model.Categorie;
import com.Fouss.boutique.Model.CategorieDTO;
import com.Fouss.boutique.Service.CategorieService;
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
@RequestMapping("/api/categorie") // Change to map to /api/employees directly
//@CrossOrigin(origins = "http://localhost:3000") // Allow CORS for React
public class CategorieController {

    @Autowired
    private CategorieService categorieService;

    // Récupérer la liste categorie

    // Récupérer un employé par ID
    @GetMapping
    public ResponseEntity<List<CategorieDTO>> getAllCategorie() {
        List<Categorie> categorieList = categorieService.getAllCategorie();

        // Convertir les catégories en DTOs
        List<CategorieDTO> categorieDTOList = categorieList.stream()
                .map(categorie -> new CategorieDTO(
                        categorie.getId(),
                        categorie.getNom(),
                        categorie.getDescription(),
                        categorie.getImage()


                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(categorieDTOList);
    }

    // Créer une nouvelle categorie
    //@PostMapping
    //public void createCategorie(@RequestBody Categorie categorie) {
    //   categorieService.CreerCategorie(categorie);
    //}

    @PostMapping
    public ResponseEntity<Categorie> createCategorie(
            @RequestParam("nom") String nom,
            @RequestParam("description") String description,
            @RequestParam("image") MultipartFile image) {

        // Logique pour sauvegarder l'image
        String imagePath = saveImage(image);  // Voir la méthode saveImage ci-dessous
        Categorie categorie = new Categorie();
        categorie.setNom(nom);
        categorie.setDescription(description);
        categorie.setImage(imagePath);  // Assurez-vous que vous avez un champ image dans votre modèle

        Categorie createdCategorie = categorieService.creerCategorie(categorie);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategorie);
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
    }







    // Mettre à jour une categorie
    @PutMapping("/{id}")
    public ResponseEntity<Categorie> updateCategorie(@PathVariable Long id, @RequestBody Categorie categorieDetails) {
        Categorie updatedCategorie = categorieService.updateCategorie(id, categorieDetails);
        return ResponseEntity.ok(updatedCategorie);
    }

    // Supprimer une categorie
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategorie(@PathVariable Long id) {
        categorieService.deleteCategorie(id);
        return ResponseEntity.noContent().build();
    }
}


