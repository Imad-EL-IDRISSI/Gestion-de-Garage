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
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonProperty("id")
    private Long id;

    @JsonProperty("vin")
    private String vin;

    @JsonProperty("immatriculation")
    private String immatriculation;

    @JsonProperty("marque")
    private String marque;

    @JsonProperty("modele")
    private String modele;

    @JsonProperty("annee")
    private String annee;

    @JsonProperty("couleur")
    private String couleur;

    @JsonProperty("km")
    private Long km;

    @JsonProperty("typeCarb")
    private Type typeCarb;

    @JsonProperty("dateAchat")
    private Date dateAchat;

    @JsonProperty("client_Id")
    private Long client_Id;

    @JsonProperty("etat")
    private Etat etat;

    @Transient @JsonProperty("client")
    private Client proprietaire;


}
