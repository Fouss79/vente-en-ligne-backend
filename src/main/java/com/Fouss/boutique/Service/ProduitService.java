package com.Fouss.boutique.Service;



import com.Fouss.boutique.Model.Produit;
import com.Fouss.boutique.Repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ProduitService {
    @Autowired
    private  final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository){
        this.produitRepository=produitRepository;
    }

    public Produit createProduit(Produit produit){
        return produitRepository.save(produit);
    }

    public List<Produit> getAllProduit() {

        return produitRepository.findAll();
    }


    public Produit getProduitById(Long id) {
        return produitRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produit non trouvé avec l'ID : " + id));
    }


}

