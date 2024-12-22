package com.example.notificationservice.model;

import com.example.notificationservice.enums.Etat;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Maintenance {

    private Long id;
    private Date debut;
    private Date fin;
    private Etat statut;

    private String desciption;
    @Transient
    private Vehicule vehicule;
    private String vehiculeId;
}
