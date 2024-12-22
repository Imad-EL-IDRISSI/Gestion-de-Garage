package com.example.factureservice.entities;

import com.example.factureservice.enums.Status;
import com.example.factureservice.model.Maintenance;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity  @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Facture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateEmission;
    private boolean etat;
    private String intervention;
    private String montantTotal;
    private Status status;
    @Transient
    private Maintenance maintenance;
    private Long id_Maintenanace;
}
