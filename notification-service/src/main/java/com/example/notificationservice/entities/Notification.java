package com.example.notificationservice.entities;

import com.example.notificationservice.model.Client;
import com.example.notificationservice.model.Vehicule;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String subject;
    private String message;
    @Transient
    private Client client;
    private Long clientId;
    @Transient
    private Vehicule vehicule;
    private Long vehiculeId;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateCreation;
}
