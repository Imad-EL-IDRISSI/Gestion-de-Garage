package com.example.maintenance.dto;

import com.example.maintenance.enums.Status;
import com.example.maintenance.model.Vehicule;
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

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class MaintenanceDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) @JsonProperty("id")
    private Long id;

    @JsonProperty("debut")
    private Date debut;
    @JsonProperty("fin")
    private Date fin;
    @JsonProperty("description")
    private String description;
    @JsonProperty("statut")
    private Status statut;
    @Transient
    private Vehicule vehicule;
    @JsonProperty("vehiculeId")
    private String vehiculeId;

}
