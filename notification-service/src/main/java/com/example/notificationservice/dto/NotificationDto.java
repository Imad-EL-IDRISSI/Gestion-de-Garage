package com.example.notificationservice.dto;

import com.example.notificationservice.model.Client;
import com.example.notificationservice.model.Vehicule;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificationDto {
    private Long id;
    private String subject;
    private String message;
    private Client client;
    private Long clientId;
    private Vehicule vehicule;
    private Long vehiculeId;
}
