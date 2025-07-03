package com.Fouss.boutique.Controller;


import com.Fouss.boutique.Model.Image;
import com.Fouss.boutique.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/carousel")
public class ImageController {

    private final Path uploadDir = Paths.get("uploads");

    @Autowired
    private ImageRepository imageRepository;


    @GetMapping("/list")
    public ResponseEntity<?> getCarouselImages() {
        return ResponseEntity.ok(imageRepository.findByVisibleInCarouselTrue());
    }


    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file,
                                         @RequestParam("description") String description,
                                         @RequestParam(value = "carousel", defaultValue = "false") boolean carousel) {
        try {
            // Créer le dossier uploads s'il n'existe pas
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = uploadDir.resolve(fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            Image image = new Image();
            image.setUrl("http://localhost:8080/uploads/" + fileName);
            image.setDescription(description);
            image.setVisibleInCarousel(carousel);
            imageRepository.save(image);

            return ResponseEntity.ok("Image uploaded successfully");

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error uploading image");
        }
    }
}

