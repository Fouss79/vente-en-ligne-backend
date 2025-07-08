package com.Fouss.boutique.Repository;


import com.Fouss.boutique.Model.Image;
import com.Fouss.boutique.Model.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image,Long> {
    List<Image> findByVisibleInCarouselTrue();
}

