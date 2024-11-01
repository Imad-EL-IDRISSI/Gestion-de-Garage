package com.example.factureservice.entities;

import com.example.factureservice.model.Client;
import com.example.factureservice.model.Maintenance;
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
    @Transient
    private Maintenance maintenance;
    private Long id_Maintenanace;
}
