package com.example.notificationservice.web;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.service.NotificationService;
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
    public ResponseEntity<String> sendNotification(@RequestBody NotificationDto notificationDto){
        notificationService.sendEmail(notificationDto);
        return ResponseEntity.ok("le message a ete envoyer a " + notificationDto.getClient().getEmail() );

    }
}
