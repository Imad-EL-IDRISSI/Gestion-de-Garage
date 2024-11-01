package com.example.notificationservice.service;

import com.example.notificationservice.clients.MaintenanceRestMaintenance;
import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.exception.NotificationNotFound;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {



    private MaintenanceRestMaintenance clientRestClient;
    private NotificationRepository notificationRepository;
    private JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepository notificationRepository, MaintenanceRestMaintenance clientRestClient, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.clientRestClient = clientRestClient;
        this.mailSender = mailSender;
    }

    @Override
    public void saveNotif(NotificationDto notificationDto) {

        Notification notification = new Notification();
        notification.setMessage(notificationDto.getMessage());
        notification.setId_Maintenance(notificationDto.getId_Maintenance());
        notification.setSubject(notificationDto.getSubject());
        notification.setDateEnvoi(notificationDto.getDateEnvoi());
        notification.setDateCreation(notificationDto.getDateCreation());
        notificationRepository.save(notification);
    }
    @Override
    public void sendEmail(Long id) {
        SimpleMailMessage message = new SimpleMailMessage();

        Optional<Notification> notification = notificationRepository.findById(id);
        System.out.println("----------------" + notification);
        if(notification.isPresent()) {
            Notification notification1 = notification.get();

            notification1.setMaintenance(clientRestClient.findMaintenanceById(notification1.getId_Maintenance()));
            message.setTo(notification1.getMaintenance().getVehicule().getProprietaire().getEmail());
            message.setSubject(notification1.getSubject());
            message.setText(contenuNotification(notification1));
        }
        mailSender.send(message);
    }
    @Override
    public Notification getNotificationById(Long id) throws NotificationNotFound {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new NotificationNotFound("Notification non trouvé avec ID " + id));

        // Associer les informations du propriétaire au véhicule
        notification.setMaintenance(clientRestClient.findMaintenanceById(notification.getId_Maintenance()));

        return notification;
    }

    @Override
    public List<Notification> allNotifications() {
        List<Notification> notificationList = notificationRepository.findAll();
        notificationList.forEach(n -> {
            n.setMaintenance(clientRestClient.findMaintenanceById(n.getId_Maintenance()));
        });
        return notificationList;
    }


    private String contenuNotification(Notification notification) {
        // Création du contenu de la notification
        StringBuilder message = new StringBuilder();
        message.append("Bonjour ").append(notification.getMaintenance().getVehicule().getProprietaire().getNom()).append(",\n\n")
                .append(notification.getMessage()).append("\n")
                .append("Merci de votre attention.\n")
                .append("Cordialement,\n")
                .append("Garage EA de Maintenance.");

        return message.toString();
    }

}
