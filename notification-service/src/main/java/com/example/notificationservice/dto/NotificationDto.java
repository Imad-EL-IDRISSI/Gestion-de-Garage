package com.example.notificationservice.dto;

import com.example.notificationservice.model.Client;
import com.example.notificationservice.model.VehiculeDto;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class NotificationDto {
    private Long id;
    private String subject;
    private String message;
    private Client client;
    private Long clientId;
    private VehiculeDto vehicule;
    private Long vehiculeId;
}
