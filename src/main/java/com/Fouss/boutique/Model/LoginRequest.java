package com.Fouss.boutique.Model;


import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String motDePasse;

    // getters et setters
}

