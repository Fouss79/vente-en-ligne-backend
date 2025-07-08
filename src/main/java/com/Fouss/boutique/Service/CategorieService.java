package com.Fouss.boutique.Service;



import com.Fouss.boutique.Model.Categorie;
import com.Fouss.boutique.Repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    private final CategorieRepository categorieRepository;

    @Autowired
    public CategorieService(CategorieRepository categorieRepository) {

        this.categorieRepository = categorieRepository;
    }

    public Categorie creerCategorie(Categorie categorie)
    {
        return categorieRepository.save(categorie);
    }
    public Categorie getCategorieById(Long id) {
        Optional<Categorie> categorie = categorieRepository.findById(id);
        return categorie.orElse(null);  // Retourne null si la catégorie n'est pas trouvée
    }
    public List<Categorie> getAllCategorie() {
        return categorieRepository.findAll();
    }

    public Optional<Categorie> getCategorieId(Long id) {
        return categorieRepository.findById(id);
    }

    public Categorie updateCategorie(Long id, Categorie categorieDetails) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        categorie.setNom(categorieDetails.getNom());
        categorie.setDescription(categorieDetails.getDescription());

        if (categorieDetails.getImage() != null) {
            categorie.setImage(categorieDetails.getImage());
        }

        return categorieRepository.save(categorie);
    }

    public void deleteCategorie(Long id) {
        Categorie categorie = categorieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));
        categorieRepository.delete(categorie);
    }
}
