package com.example.maintenance.model;

import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter @ToString
public class Vehicule {

   private String vin;

   private String immatriculation;

   private String marque;

   private String modele;

   private String annee;

   private String couleur;

   private Long km;


   private Date dateAchat;

   private String client_Id;


   @Transient
   private Client proprietaire;

}
