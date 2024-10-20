package com.example.notificationservice.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Client {
    private long id;

    private String cin;
    private String nom;
    private String prenom;
    private String adresse;
    private String telephone;

    private String email;
}
