package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;

public interface NotificationService {
    void sendEmail(NotificationDto notificationDto);
}
