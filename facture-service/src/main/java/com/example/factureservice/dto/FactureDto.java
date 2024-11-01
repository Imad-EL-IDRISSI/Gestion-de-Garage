package com.example.factureservice.dto;

import com.example.factureservice.model.Client;
import com.example.factureservice.model.Maintenance;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Setter @Getter
public class FactureDto {
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
