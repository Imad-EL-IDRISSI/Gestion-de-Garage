package com.example.notificationservice.dto;

import com.example.notificationservice.enums.Status;
import com.example.notificationservice.model.Client;
import com.example.notificationservice.model.Maintenance;
import com.example.notificationservice.model.Vehicule;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor
public class NotificationDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Status status;
    @Transient
    private Maintenance maintenance;
    private Long id_Maintenance;
    private LocalDateTime dateEnvoi;
}
