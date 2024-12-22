package com.example.notificationservice.service;

import com.example.notificationservice.clients.MaintenanceRestMaintenance;
import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.enums.Status;
import com.example.notificationservice.exception.NotificationNotFound;
import com.example.notificationservice.repository.NotificationRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        notification.setIsSent(false);
        notification.setStatus(Status.valueOf("NONENVOYE"));
        notification.setId_Maintenance(notificationDto.getId_Maintenance());
        notification.setDateEnvoi(LocalDateTime.now()); // Génère la date actuelle
        notificationRepository.save(notification);
    }

    @Override
    public void sendEmail(Long id) {
        SimpleMailMessage message = new SimpleMailMessage();

        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            notification.setIsSent(true); // Marque la notification comme envoyée
            notification.setDateEnvoi(LocalDateTime.now()); // Enregistre la date et l'heure actuelles
            notification.setMaintenance(clientRestClient.findMaintenanceById(notification.getId_Maintenance())); // Récupère la maintenance associée

            notificationRepository.save(notification); // Sauvegarde la notification mise à jour

            message.setTo(notification.getMaintenance().getVehicule().getProprietaire().getEmail());
            message.setText(contenuNotification(notification));

            mailSender.send(message);

            // Retourne la notification mise à jour, y compris la date et l'heure d'envoi
        }
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
        // Vérification des données nécessaires pour éviter les NullPointerException
        if (notification == null || notification.getMaintenance() == null ||
                notification.getMaintenance().getVehicule() == null ||
                notification.getMaintenance().getVehicule().getProprietaire() == null) {
            throw new IllegalArgumentException("Les informations de la notification sont incomplètes.");
        }

        // Récupération des informations
        String nomProprietaire = notification.getMaintenance().getVehicule().getProprietaire().getNom();
        String immatriculationVehicule = notification.getMaintenance().getVehicule().getImmatriculation(); // Si disponible

        // Création du contenu de la notification
        StringBuilder message = new StringBuilder();
        message.append("Bonjour ").append(nomProprietaire).append(",\n\n")
                .append("Nous vous informons que votre véhicule ")
                .append("(immatriculation : ").append(immatriculationVehicule).append(") ")
                .append("est désormais passé à la phase de maintenance. Nos équipes techniques sont mobilisées ")
                .append("pour effectuer les interventions nécessaires conformément aux standards de qualité de notre service.\n\n")
                .append("Nous vous tiendrons informé(e) dès que les travaux seront terminés ou si des informations supplémentaires sont requises de votre part.\n\n")
                .append("Merci de votre attention.\n\n")
                .append("Cordialement,\n")
                .append("Garage EA de Maintenance.");

        return message.toString();
    }


}
