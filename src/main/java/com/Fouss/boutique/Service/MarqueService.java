package com.Fouss.boutique.Service;

import com.Fouss.boutique.Model.Marque;
import com.Fouss.boutique.Repository.MarqueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarqueService {

    private final MarqueRepository marqueRepository;

    @Autowired
    public MarqueService(MarqueRepository marqueRepository) {

        this.marqueRepository = marqueRepository;
    }

    public Marque creerMarque(Marque marque)
    {
        return marqueRepository.save(marque);
    }
    public Marque getMarqueById(Long id) {
        Optional<Marque> marque = marqueRepository.findById(id);
        return marque.orElse(null);  // Retourne null si la catégorie n'est pas trouvée
    }
    public List<Marque> getAllMarque() {
        return marqueRepository.findAll();
    }

    public Optional<Marque> getMarqueId(Long id) {
        return marqueRepository.findById(id);
    }

    public Marque updateMarque(Long id, Marque marqueDetails) {
        Marque marque = marqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marque non trouvée"));

        marque.setNom(marqueDetails.getNom());
        marque.setDescription(marqueDetails.getDescription());

        if (marqueDetails.getImage() != null) {
            marque.setImage(marqueDetails.getImage());
        }

        return marqueRepository.save(marque);
    }

    public void deleteMarque(Long id) {
        Marque marque = marqueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marque non trouvée"));
        marqueRepository.delete(marque);
    }
}

