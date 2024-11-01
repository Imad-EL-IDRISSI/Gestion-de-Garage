package com.example.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Vehicule {
    private String vin;

    private String immatriculation;

    private String marque;

    private String modele;

    private String annee;

    private String couleur;

    private Long km;

    private String typeCarb;

    private Date dateAchat;

    private String client_Id;

    private String etat;

    private Client proprietaire;



}
