package com.Fouss.boutique.Controller;


import com.Fouss.boutique.Model.Collection;
import com.Fouss.boutique.Model.CollectionAccueil;
import com.Fouss.boutique.Model.CollectionDTO;
import com.Fouss.boutique.Service.CollectionAccueilService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/collection-accueil")
@CrossOrigin(origins = "*")
public class CollectionAccueilController {

    private final CollectionAccueilService accueilService;

    public CollectionAccueilController(CollectionAccueilService accueilService) {
        this.accueilService = accueilService;
    }

    @GetMapping
    public CollectionDTO getCollectionAccueil() {
        Collection collection = accueilService.getCollectionAccueil()
                .map(acc -> acc.getCollection())
                .orElse(null);

        if (collection == null) return null;

        // Mapper manuellement vers CollectionDTO
        CollectionDTO dto = new CollectionDTO();
        dto.setId(collection.getId());
        dto.setNom(collection.getNom());
        dto.setDescription(collection.getDescription());
        dto.setImage(collection.getImage());
        dto.setProduits(collection.getProduits());

        return dto;
    }

    @PostMapping("/{collectionId}")
    public CollectionAccueil definirCollectionAccueil(@PathVariable Long collectionId) {
        return accueilService.definirCollectionAccueil(collectionId);
    }

}
