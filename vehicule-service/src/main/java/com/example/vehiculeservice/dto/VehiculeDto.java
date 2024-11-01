package com.example.vehiculeservice.dto;

import com.example.vehiculeservice.enums.Etat;
import com.example.vehiculeservice.enums.Type;
import com.example.vehiculeservice.model.Client;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VehiculeDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonProperty("id")
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
    private String client_Id;

    @JsonProperty("etat")
    private Etat etat;

    @Transient @JsonProperty("client")
    private Client proprietaire;
}
