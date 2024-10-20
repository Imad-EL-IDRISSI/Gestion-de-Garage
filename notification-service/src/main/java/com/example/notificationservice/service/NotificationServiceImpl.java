package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;

    private JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepository notificationRepository, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(NotificationDto notificationDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        Notification notification = new Notification();
        notification.setClient(notificationDto.getClient());
        notification.setClientId(notificationDto.getClientId());
        notification.setMessage(notificationDto.getMessage());
        notification.setVehicule(notificationDto.getVehicule());
        notification.setVehiculeId(notificationDto.getVehiculeId());

        notificationRepository.save(notification);

        message.setTo(notificationDto.getClient().getEmail());
        message.setSubject(notificationDto.getSubject());
        message.setText(contenuNotification(notification));


        mailSender.send(message);

    }
    private String contenuNotification(Notification notification) {
        // Création du contenu de la notification
        StringBuilder message = new StringBuilder();
        message.append("Bonjour ").append(notification.getClient().getNom()).append(",\n\n")
                .append("Vous avez reçu une nouvelle notification :\n")
                .append("Message : ").append(notification.getMessage()).append("\n")
                .append("Merci de votre attention.\n")
                .append("Cordialement,\n")
                .append("Votre équipe de maintenance.");

        return message.toString();
    }


}
