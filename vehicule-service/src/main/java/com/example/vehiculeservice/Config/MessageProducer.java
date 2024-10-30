package com.example.vehiculeservice.Config;

import com.example.vehiculeservice.dto.VehiculeDto;
import com.example.vehiculeservice.entities.Vehicule;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final KafkaTemplate<String, VehiculeDto> kafkaTemplate; // Changez ici le type de KafkaTemplate

    public MessageProducer(KafkaTemplate<String, VehiculeDto> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, VehiculeDto vehicule) { // Changez le type du message
        kafkaTemplate.send(topic, vehicule);
    }

}