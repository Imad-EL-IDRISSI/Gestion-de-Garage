package com.example.factureservice.service;


import com.example.factureservice.clients.MaintenanceRestMaintenance;
import com.example.factureservice.dto.FactureDto;
import com.example.factureservice.entities.Facture;
import com.example.factureservice.exception.FactureNotFound;
import com.example.factureservice.repository.FactureRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FactureServiceImpl implements FactureService {


    private MaintenanceRestMaintenance clientRestClient;
    private FactureRepository factureRepository;
    private JavaMailSender mailSender;

    public FactureServiceImpl(MaintenanceRestMaintenance clientRestClient, FactureRepository factureRepository, JavaMailSender mailSender) {
        this.clientRestClient = clientRestClient;
        this.factureRepository = factureRepository;
        this.mailSender = mailSender;
    }

    @Override
    public void saveFacture(FactureDto facDto) {

        Facture facture = new Facture();

        facture.setNumeroFacture(facDto.getNumeroFacture());
        facture.setDateEmission(facDto.getDateEmission());
        facture.setEtat(facDto.isEtat());
        facture.setId_Maintenanace(facDto.getId_Maintenanace());
        facture.setIntervention(facDto.getIntervention());
        facture.setMontantTotal(facDto.getMontantTotal());

        factureRepository.save(facture);

    }
    @Override
    public List<Facture> allFactures(){
        List<Facture> factureList =  factureRepository.findAll();
        factureList.forEach(f -> {
            f.setMaintenance(clientRestClient.findMaintenanceById(f.getId_Maintenanace()));
        });
        return factureList;
    }
    @Override
    public  Facture getFactureById(Long id) throws FactureNotFound {
        Optional<Facture> facture = factureRepository.findById(id);

        if(facture.isPresent()){
            Facture facture1 = facture.get();

            facture1.setMaintenance(clientRestClient.findMaintenanceById(facture1.getId_Maintenanace()));
            return facture1;
        }else {
            throw new FactureNotFound("la facture n'exite pas");
        }




    }

    @Override
    public void envoyerEmailFacture(Long id) {
        Optional<Facture> factureOpt = factureRepository.findById(id);

        if (factureOpt.isPresent()) {
            Facture facture = factureOpt.get();

            facture.setMaintenance(clientRestClient.findMaintenanceById(facture.getId_Maintenanace()));

            // Création du message email
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(facture.getMaintenance().getVehicule().getProprietaire().getEmail());
            message.setSubject(facture.getNumeroFacture());
            message.setText(contenuEmail(facture));

            factureRepository.save(facture);
            // Envoi de l'email
            mailSender.send(message);
        }
    }

        private String contenuEmail(Facture facDto){
            // Création du contenu de l'email
            return "Bonjour " + facDto.getMaintenance().getVehicule().getProprietaire().getNom()+ ",\n\n"
                    + "Veuillez trouver ci-dessous les détails de votre facture :\n"
                    + "Numéro de facture : " + facDto.getNumeroFacture() + "\n"
                    + "Date d'émission : " + facDto.getDateEmission() + "\n"
                    + "Montant total : " + facDto.getMontantTotal() + " €\n"
                    + "État du paiement : " + facDto.isEtat() + "\n\n"
                    + "Merci pour votre confiance.\n"
                    + "Cordialement,\n"
                    + "Garage EA de Maintenance.";
        }
    }

