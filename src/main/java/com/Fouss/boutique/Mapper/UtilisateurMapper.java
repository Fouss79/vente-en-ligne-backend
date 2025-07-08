package com.Fouss.boutique.Mapper;

import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Model.UtilisateurDTO;

public class UtilisateurMapper {

    public static UtilisateurDTO toDTO(Utilisateur utilisateur) {
        return new UtilisateurDTO(
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getPrenom(),
                utilisateur.getEmail(),
                utilisateur.getTelephone(),
                utilisateur.getAdresse(),
                utilisateur.getRole().toString(),
                utilisateur.getImage()

        );
    }
}

