package com.example.vehiculeservice.service;

import com.example.vehiculeservice.Config.MessageProducer;
import com.example.vehiculeservice.Repository.VehiculeRepository;
import com.example.vehiculeservice.clients.ClientRestClient;
import com.example.vehiculeservice.dto.VehiculeDto;
import com.example.vehiculeservice.entities.Vehicule;
import com.example.vehiculeservice.exception.VehiculeNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService{

    private VehiculeRepository vehiculeRepository;
    private ClientRestClient clientRestClient;
    private final MessageProducer messageProducer; // Injection de MessageProducer


    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository, ClientRestClient clientRestClient, MessageProducer messageProducer) {
        this.vehiculeRepository = vehiculeRepository;
        this.clientRestClient = clientRestClient;
        this.messageProducer = messageProducer;
    }

    @Override
    public Vehicule saveVehicule(VehiculeDto vehiculeDto) {
        Vehicule vehicule = new Vehicule();

        vehicule.setVin(vehiculeDto.getVin());
        vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
        vehicule.setMarque(vehiculeDto.getMarque());
        vehicule.setModele(vehiculeDto.getModele());
        vehicule.setAnnee(vehiculeDto.getAnnee());
        vehicule.setKm(vehiculeDto.getKm());
        vehicule.setCouleur(vehiculeDto.getCouleur());
        vehicule.setTypeCarb(vehiculeDto.getTypeCarb());
        vehicule.setDateAchat(vehiculeDto.getDateAchat());
        vehicule.setProprietaire(vehiculeDto.getProprietaire());
        vehicule.setClient_Id(vehiculeDto.getClient_Id());
        vehicule.setEtat(vehiculeDto.getEtat());
        vehicule.setCouleur(vehiculeDto.getCouleur());
        System.out.println("----------------------------------------");


        return vehiculeRepository.save(vehicule);
    }

    @Override
    public Vehicule updateVehicule(VehiculeDto vehiculeDto, Long id) throws VehiculeNotFound {
        Optional<Vehicule> existingVehicule = vehiculeRepository.findById(id);

        if(existingVehicule.isPresent()){
            Vehicule vehicule = existingVehicule.get();
            if(vehiculeDto.getVin() != null){
                vehicule.setVin(vehiculeDto.getVin());
            }
            if(vehiculeDto.getImmatriculation() != null){
                vehicule.setImmatriculation(vehiculeDto.getImmatriculation());
            }
            if(vehiculeDto.getMarque() != null){
                vehicule.setMarque(vehiculeDto.getMarque());
            }
            if(vehiculeDto.getModele() != null){
                vehicule.setModele(vehiculeDto.getModele());
            }
            if(vehiculeDto.getAnnee() != null){
                vehicule.setAnnee(vehiculeDto.getAnnee());
            }
            if(vehiculeDto.getCouleur() != null){
                vehicule.setCouleur(vehiculeDto.getCouleur());
            }
            if(vehiculeDto.getKm() != null){
                vehicule.setKm(vehiculeDto.getKm());
            }
            if(vehiculeDto.getTypeCarb() != null){
                vehicule.setTypeCarb(vehiculeDto.getTypeCarb());
            }
            if(vehiculeDto.getDateAchat() != null){
                vehicule.setDateAchat(vehiculeDto.getDateAchat());
            }
            if(vehiculeDto.getProprietaire() != null){
                vehicule.setProprietaire(vehiculeDto.getProprietaire());
            }
            if(vehiculeDto.getEtat() != null){
                vehicule.setEtat(vehiculeDto.getEtat());
            }
            return vehiculeRepository.save(vehicule);
        }else {
            throw new VehiculeNotFound("Vehicule not found with ID"+id);
        }

    }

    @Override
    public List<Vehicule> allVehicules() {
        List<Vehicule> vehiculeList = vehiculeRepository.findAll();
        vehiculeList.forEach(vehicule -> {
            vehicule.setProprietaire(clientRestClient.findClientById(vehicule.getClient_Id()));
            System.out.println("::::::::::::::::::::"+vehicule.getProprietaire());
        });
        return vehiculeList;
    }
    @Override
    public Vehicule getVehiculeById(Long id) throws  VehiculeNotFound{
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);

        if (optionalVehicule.isPresent()) {
            Vehicule vehicule = optionalVehicule.get();
            // Récupérer les informations du propriétaire du véhicule
            vehicule.setProprietaire(clientRestClient.findClientById(vehicule.getClient_Id()));

            // Créer un VehiculeDto pour envoyer les informations du véhicule
            VehiculeDto vehiculeDto = new VehiculeDto();
            vehiculeDto.setId(vehicule.getId());
            vehiculeDto.setVin(vehicule.getVin());
            vehiculeDto.setImmatriculation(vehicule.getImmatriculation());
            vehiculeDto.setMarque(vehicule.getMarque());
            vehiculeDto.setModele(vehicule.getModele());
            vehiculeDto.setAnnee(vehicule.getAnnee());
            vehiculeDto.setKm(vehicule.getKm());
            vehiculeDto.setCouleur(vehicule.getCouleur());
            vehiculeDto.setTypeCarb(vehicule.getTypeCarb());
            vehiculeDto.setDateAchat(vehicule.getDateAchat());
            vehiculeDto.setProprietaire(vehicule.getProprietaire());
            vehiculeDto.setClient_Id(vehicule.getClient_Id());
            vehiculeDto.setEtat(vehicule.getEtat());

            if(vehiculeDto.getId() == id) {
                // Envoi du message au topic Kafka pour le véhicule consulté
                messageProducer.sendMessage("vehicule-topic", vehiculeDto);
            }
            return vehicule;
        } else {
            throw new VehiculeNotFound("Véhicule non trouvé avec ID " + id);
        }
    }

}
