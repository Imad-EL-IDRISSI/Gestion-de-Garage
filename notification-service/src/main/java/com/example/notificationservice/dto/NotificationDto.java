package com.example.notificationservice.dto;

import com.example.notificationservice.model.Client;
import com.example.notificationservice.model.Maintenance;
import com.example.notificationservice.model.Vehicule;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class NotificationDto {
    private Long id;
    private String subject;
    private String message;
    private Maintenance maintenance;
    private Long id_Maintenance;
    private LocalDateTime dateEnvoi;
    private LocalDateTime dateCreation;
}
