package com.example.notificationservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class VehiculeDto {
    @JsonProperty("id")
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
    private String typeCarb;

    @JsonProperty("dateAchat")
    private Date dateAchat;

    @JsonProperty("client_Id")
    private Long client_Id;

    @JsonProperty("etat")
    private String etat;

   @JsonProperty("client")
    private Client proprietaire;
    @Override
    public String toString() {
        return "VehiculeDto{" +
                "id=" + id +
                ", vin='" + vin + '\'' +
                ", immatriculation='" + immatriculation + '\'' +
                ", marque='" + marque + '\'' +
                ", modele='" + modele + '\'' +
                ", annee='" + annee + '\'' +
                ", couleur='" + couleur + '\'' +
                ", km=" + km +
                ", typeCarb='" + typeCarb + '\'' +
                ", dateAchat=" + dateAchat +
                ", client_Id=" + client_Id +
                ", etat='" + etat + '\'' +
                ", proprietaire=" + proprietaire +
                '}';
    }


}
