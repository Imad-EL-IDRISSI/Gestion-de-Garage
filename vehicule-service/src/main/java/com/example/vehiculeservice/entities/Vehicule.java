package com.example.vehiculeservice.entities;

import com.example.vehiculeservice.enums.Etat;
import com.example.vehiculeservice.enums.Type;
import com.example.vehiculeservice.model.Client;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Vehicule {
    @Id
    private String vin;

    private String immatriculation;

    private String marque;

    private String modele;

    private String annee;

    private String couleur;

    private Long km;

    private Type typeCarb;

    private Date dateAchat;

    private String client_Id;

    private Etat etat;

    @Transient
    private Client proprietaire;


}
