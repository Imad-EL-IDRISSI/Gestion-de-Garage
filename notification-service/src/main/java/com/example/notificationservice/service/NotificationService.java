package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.exception.NotificationNotFound;

import java.util.List;


public interface NotificationService {
    void saveNotif(NotificationDto notificationDto);



    void sendEmail(Long idNotif);

    Notification getNotificationById(Long id) throws NotificationNotFound;

    List<Notification> allNotifications();
}
