package com.example.notificationservice.model;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Vehicule {
    private Long id;

    private String vin;
    private String immatriculation;
    private String marque;
    private String modele;
    private String annee;
    private String Couleur;
    private Long km;
    private Date dateAchat;


}
