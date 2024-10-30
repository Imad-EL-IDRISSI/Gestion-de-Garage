package com.example.notificationservice.web;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationController {

    private NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/notif")
    public ResponseEntity<String> sendNotification() {
        // Si vous souhaitez envoyer toutes les notifications de la queue
        // vous pouvez les traiter ici. Sinon, cette méthode peut simplement
        // servir de déclencheur.

        // Envoyer une notification en utilisant une notification de la queue (optionnel)
        NotificationDto notificationDto = notificationService.getNotificationFromQueue();
        System.out.println("-------------"+notificationDto);
        if (notificationDto != null) {
            notificationService.sendEmail(notificationDto);
            return ResponseEntity.ok("Notification envoyée à " + notificationDto.getClient().getEmail());
        } else {
            return ResponseEntity.ok("Aucune notification à envoyer.");
        }
    }
}
