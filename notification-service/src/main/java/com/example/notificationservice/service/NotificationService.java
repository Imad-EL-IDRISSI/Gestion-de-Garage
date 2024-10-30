package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;

public interface NotificationService {
    void sendEmail(NotificationDto notificationDto);

    // Consommateur Kafka pour écouter les messages sur le topic "vehicule-topic"
    @KafkaListener(topics = "vehicule-topic", groupId = "vehicule-grp")
    void consumeVehiculeData(String message) throws JsonProcessingException;

    NotificationDto getNotificationFromQueue(Long vehiculeId);

    NotificationDto getNotificationFromQueue();
}
