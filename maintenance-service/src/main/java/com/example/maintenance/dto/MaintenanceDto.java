package com.example.maintenance.dto;

import com.example.maintenance.enums.Status;
import com.example.maintenance.model.Vehicule;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MaintenanceDto {
    private Long id;
    private Date debut;
    private Date fin;
    private String desciption;
    private Status statut;
    private Vehicule vehicule;
}
