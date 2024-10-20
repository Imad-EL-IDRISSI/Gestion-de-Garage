package com.example.factureservice.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class Client {
    private long id;
    private String cin;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;
    private String email;
}
