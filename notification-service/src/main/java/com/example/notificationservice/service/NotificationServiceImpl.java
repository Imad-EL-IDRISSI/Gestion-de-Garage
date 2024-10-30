package com.example.notificationservice.service;

import com.example.notificationservice.dto.NotificationDto;
import com.example.notificationservice.entities.Notification;
import com.example.notificationservice.model.VehiculeDto;
import com.example.notificationservice.repository.NotificationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    // Utiliser une Queue pour stocker les notifications à envoyer
    private LinkedBlockingQueue<NotificationDto> notificationQueue = new LinkedBlockingQueue<>();

    // Utiliser une Map pour stocker les notifications avec vehiculeId comme clé
    private Map<Long, NotificationDto> notificationMap = new ConcurrentHashMap<>();

    private NotificationRepository notificationRepository;
    private ObjectMapper objectMapper;
    private JavaMailSender mailSender;

    public NotificationServiceImpl(NotificationRepository notificationRepository, ObjectMapper objectMapper, JavaMailSender mailSender) {
        this.notificationRepository = notificationRepository;
        this.objectMapper = objectMapper;
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(NotificationDto notificationDto) {
        SimpleMailMessage message = new SimpleMailMessage();

        // Créer une nouvelle notification
        Notification notification = new Notification();
        notification.setClient(notificationDto.getClient());
        notification.setClientId(notificationDto.getClientId());
        notification.setMessage(notificationDto.getMessage());
        notification.setVehicule(notificationDto.getVehicule());
        notification.setVehiculeId(notificationDto.getVehiculeId());

        // Sauvegarder la notification dans la base de données
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

    // Consommateur Kafka pour écouter les messages sur le topic "vehicule-topic"
    @Override
    @KafkaListener(topics = "vehicule-topic", groupId = "vehicule-grp")
    public void consumeVehiculeData(String message) throws JsonProcessingException {
        // Traitez le message comme vous l'avez déjà fait
        VehiculeDto vehiculeDto = objectMapper.readValue(message, VehiculeDto.class);
        JsonNode jsonNode = objectMapper.valueToTree(vehiculeDto);

        // Création d'un NotificationDto pour l'envoi d'email
        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setVehicule(vehiculeDto);
        notificationDto.setVehiculeId(vehiculeDto.getId());

        // Récupération d'informations supplémentaires depuis jsonNode
        String immatriculation = jsonNode.path("immatriculation").asText();
        String marque = jsonNode.path("marque").asText();
        String modele = jsonNode.path("modele").asText();

        // Construction du message de notification
        notificationDto.setMessage("Nouveau véhicule ajouté : " + marque + " " + modele + " (Immatriculation: " + immatriculation + ")");
        notificationDto.setSubject("Notification de véhicule");

        // Ajouter la notification à la map avec vehiculeId
        notificationMap.put(vehiculeDto.getId(), notificationDto);

        // Offrir la notification dans la queue
        notificationQueue.offer(notificationDto);

        // Envoyer l'email immédiatement
        sendEmail(notificationDto);
    }

    @Override
    public NotificationDto getNotificationFromQueue(Long vehiculeId) {
        // Récupérer la notification à partir de la map en fonction de l'ID du véhicule
        return notificationMap.get(vehiculeId);
    }

    @Override
    public NotificationDto getNotificationFromQueue() {
        // Retourner la prochaine notification de la queue
        return notificationQueue.poll();
    }
}
