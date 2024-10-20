package com.example.vehiculeservice.dto;

import com.example.vehiculeservice.enums.Etat;
import com.example.vehiculeservice.enums.Type;
import com.example.vehiculeservice.model.Client;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class VehiculeDto {
    private Long id;

    private String vin;
    private String immatriculation;
    private String marque;
    private String modele;
    private String annee;
    private String Couleur;
    private Long km;
    private Type typeCarb;
    private Date dateAchat;
    private Client proprietaire;
    private Etat etat;
}
