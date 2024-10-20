package com.example.maintenance.entities;

import com.example.maintenance.enums.Status;
import com.example.maintenance.model.Vehicule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Maintenance {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date debut;
    private Date fin;
    private String desciption;
    private Status statut;
    @Transient
    private Vehicule vehicule;
    private Long veihiculeId;

}
