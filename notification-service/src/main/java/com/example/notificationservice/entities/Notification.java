package com.example.notificationservice.entities;

import com.example.notificationservice.model.Maintenance;
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
    private Maintenance maintenance;
    private Long id_Maintenance;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateCreation;
}
