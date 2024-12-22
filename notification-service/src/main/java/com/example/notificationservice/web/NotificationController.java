package com.example.notificationservice.web;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.exception.NotificationNotFound;
import com.example.notificationservice.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notification")

public class NotificationController {

    private NotificationService notificationService;


    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<String> saveNotification(@RequestBody NotificationDto notificationDto) {

        if (notificationDto != null) {
            notificationService.saveNotif(notificationDto);
            return ResponseEntity.ok("Notification saved " );
        } else {
            return ResponseEntity.ok("Aucune notification saved.");
        }
    }

    @PostMapping("/send/{id}")
    public ResponseEntity<String> sendNotification(@PathVariable Long id) {
        notificationService.sendEmail(id);
        return ResponseEntity.ok("Notification envoyée  ");
    }
    @GetMapping("/{id}")
    public  ResponseEntity<Notification> getNotificationById(@PathVariable Long id) throws NotificationNotFound {
        Notification notification = notificationService.getNotificationById(id);
        return ResponseEntity.ok(notification);
    }

    @GetMapping()
    public  ResponseEntity<List<Notification>> getAllNotif(){
        List<Notification> notificationList = notificationService.allNotifications();
        return ResponseEntity.ok(notificationList);
    }
}
