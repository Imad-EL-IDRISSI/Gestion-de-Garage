package com.example.factureservice.service;


import com.example.factureservice.dto.FactureDto;
import com.example.factureservice.entities.Facture;
import com.example.factureservice.repository.FactureRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FactureServiceImpl implements FactureService{


    private FactureRepository factureRepository;
    private JavaMailSender mailSender;

    public FactureServiceImpl(FactureRepository factureRepository, JavaMailSender mailSender) {
        this.factureRepository = factureRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void envoyerEmailFacture(FactureDto facDto) {

        Facture facture = new Facture();

        facture.setNumeroFacture(facDto.getNumeroFacture());
        facture.setDateEmission(facDto.getDateEmission());
        facture.setEtat(facDto.getEtat());
        facture.setClientId(facture.getClientId());
        facture.setIntervention(facDto.getIntervention());
        facture.setMontantTotal(facture.getMontantTotal());

        // Création du message email
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(facDto.getClient().getEmail());
        message.setSubject(facDto.getNumeroFacture());
        message.setText(contenuEmail(facDto));

        factureRepository.save(facture);
        // Envoi de l'email
        mailSender.send(message);
        System.out.println("Email envoyé avec succès à " + facDto.getClient());
    }

    private String contenuEmail(FactureDto facDto) {
        // Création du contenu de l'email
        return "Bonjour " + facDto.getClient().getNom() + ",\n\n"
                + "Veuillez trouver ci-dessous les détails de votre facture :\n"
                + "Numéro de facture : " + facDto.getNumeroFacture() + "\n"
                + "Date d'émission : " + facDto.getDateEmission() + "\n"
                + "Montant total : " + facDto.getMontantTotal() + " €\n"
                + "État du paiement : " + facDto.getEtat() + "\n\n"
                + "Merci pour votre confiance.\n"
                + "Cordialement,\n"
                + "Votre équipe de maintenance.";
    }
}
