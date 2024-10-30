package com.example.notificationservice.config;

import com.example.notificationservice.model.VehiculeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
    private ObjectMapper objectMapper;

    public MessageConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "vehicule-topic", groupId = "vehicule-grp")
    public void listen(String message) {
        try {
            // Désérialiser le message JSON en objet VehiculeDto
           VehiculeDto vehiculeDto = objectMapper.readValue(message, VehiculeDto.class);
            com.fasterxml.jackson.databind.JsonNode jsonNode = objectMapper.valueToTree(vehiculeDto);

            // Print the JSON representation
            System.out.println("Received message: " + jsonNode.getClass());
            System.out.println("Received message: " + jsonNode.toPrettyString());
            //System.out.println("Received message: " + vehiculeDto.getImmatriculation());
            System.out.println("Received message: " + vehiculeDto);
            System.out.println("Received message: " + message);



            // Traitez le véhicule ici
        } catch (Exception e) {
            e.printStackTrace(); // Gérer l'erreur de désérialisation
        }
    }
}

