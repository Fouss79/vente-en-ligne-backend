package com.Fouss.boutique.Service;




import com.Fouss.boutique.Model.Collection;
import com.Fouss.boutique.Model.CollectionAccueil;
import com.Fouss.boutique.Repository.CollectionAccueilRepository;
import com.Fouss.boutique.Repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CollectionAccueilService {

    private final CollectionAccueilRepository accueilRepository;
    private final CollectionRepository collectionRepository;

    public CollectionAccueilService(CollectionAccueilRepository accueilRepository,
                                    CollectionRepository collectionRepository) {
        this.accueilRepository = accueilRepository;
        this.collectionRepository = collectionRepository;
    }

    public CollectionAccueil definirCollectionAccueil(Long collectionId) {
        Collection collection = collectionRepository.findById(collectionId)
                .orElseThrow(() -> new RuntimeException("Collection non trouvée"));

        CollectionAccueil accueil = accueilRepository.findAll().stream().findFirst()
                .orElse(new CollectionAccueil());

        accueil.setCollection(collection);
        return accueilRepository.save(accueil);
    }

    public Optional<CollectionAccueil> getCollectionAccueil() {
        return accueilRepository.findAll().stream().findFirst();
    }
}
