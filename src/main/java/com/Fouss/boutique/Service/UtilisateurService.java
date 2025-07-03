package com.Fouss.boutique.Service;



import com.Fouss.boutique.Model.Role;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    public List<Utilisateur> getAllUtilisateur() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur createUtilisateur(Utilisateur utilisateur) {

        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUtlisateur(Long id, Utilisateur UserDetails) {
        Utilisateur user = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Client non trouvé"));

        user.setNom(UserDetails.getNom());
        user.setPrenom(UserDetails.getPrenom());
        user.setTelephone(UserDetails.getTelephone());
        user.setAdresse(user.getAdresse());
        user.setEmail(UserDetails.getEmail());
        user.setMotDePasse(UserDetails.getMotDePasse());

        return utilisateurRepository.save(user);
    }



    public void updateUserRole(Long userId, String roleStr) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable avec l'id : " + userId));

        try {
            Role role = Role.valueOf(roleStr.toUpperCase());
            utilisateur.setRole(role);
            utilisateurRepository.save(utilisateur);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Statut non reconnu : " + roleStr);
        }
    }


    public void deleteUtilisateur(Long id) {
        Utilisateur user = utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Employé non trouvé"));
        utilisateurRepository.delete(user);
    }

}