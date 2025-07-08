package com.Fouss.boutique.Controller;

import com.Fouss.boutique.Model.LoginRequest;
import com.Fouss.boutique.Model.Utilisateur;
import com.Fouss.boutique.Repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Optional<Utilisateur> utilisateurOpt = utilisateurRepository.findByEmail(loginRequest.getEmail());

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            if (utilisateur.getMotDePasse().equals(loginRequest.getMotDePasse())) {
                utilisateur.setMotDePasse(null);

                // Génération d'un token simple aléatoire (à remplacer par un vrai JWT plus tard)
                String token = UUID.randomUUID().toString();

                // Réponse simplifiée avec token + infos utilisateur
                return ResponseEntity.ok(new LoginResponse(
                        utilisateur.getId(),
                        utilisateur.getEmail(),
                        utilisateur.getRole().toString(),  // adapte selon ton modèle
                        token
                ));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
    }

    // Classe interne ou à mettre dans un fichier séparé
    static class LoginResponse {
        private Long id;
        private String email;
        private String role;
        private String token;

        public LoginResponse(Long id, String email, String role, String token) {
            this.id = id;
            this.email = email;
            this.role = role;
            this.token = token;
        }

        // getters et setters
        public Long getId() { return id; }
        public String getEmail() { return email; }
        public String getRole() { return role; }
        public String getToken() { return token; }

        public void setId(Long id) { this.id = id; }
        public void setEmail(String email) { this.email = email; }
        public void setRole(String role) { this.role = role; }
        public void setToken(String token) { this.token = token; }
    }
}
