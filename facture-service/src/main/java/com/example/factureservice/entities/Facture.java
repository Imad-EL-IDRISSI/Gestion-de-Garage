package com.example.factureservice.entities;

import com.example.factureservice.model.Client;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity  @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numeroFacture;
    private Date dateEmission;
    private boolean etat;
    private String intervention;
    private String montantTotal;
    private String emailClient;
    /*@Transient
    private Client client;*/

    private Long clientId;
}
