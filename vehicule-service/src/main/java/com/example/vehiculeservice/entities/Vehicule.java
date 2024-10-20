package com.example.vehiculeservice.entities;

import com.example.vehiculeservice.enums.Etat;
import com.example.vehiculeservice.enums.Type;
import com.example.vehiculeservice.model.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Transient
    private Client proprietaire;
    private Long proprietaireId;
    private Etat etat;

}
