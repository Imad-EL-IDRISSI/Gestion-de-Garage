package com.example.vehiculeservice.service;

import com.example.vehiculeservice.Repository.VehiculeRepository;
import com.example.vehiculeservice.clients.ClientRestClient;
import com.example.vehiculeservice.dto.VehiculeDto;
import com.example.vehiculeservice.entities.Vehicule;
import com.example.vehiculeservice.enums.Etat;
import com.example.vehiculeservice.exception.VehiculeNotFound;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VehiculeServiceImpl implements VehiculeService{

    private final RestTemplate restTemplate;

    private VehiculeRepository vehiculeRepository;
    private ClientRestClient clientRestClient;


    public VehiculeServiceImpl(RestTemplate restTemplate, VehiculeRepository vehiculeRepository, ClientRestClient clientRestClient) {
        this.restTemplate = restTemplate;
        this.vehiculeRepository = vehiculeRepository;
        this.clientRestClient = clientRestClient;
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
    public List<Vehicule> getVehiculesFonctionnels() {
        return vehiculeRepository.findAll().stream()
                .filter(vehicule -> "ENATTENTE".equalsIgnoreCase(String.valueOf(vehicule.getEtat())))
                .collect(Collectors.toList());
    }
    @Override
    public Vehicule updateVehicule(VehiculeDto vehiculeDto, String id) throws VehiculeNotFound {
        Optional<Vehicule> existingVehicule = vehiculeRepository.findByVin(id);

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
    public void updateEtatVehicule(Long id, String etat) {
        Vehicule vehicule = vehiculeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Véhicule non trouvé"));
        vehicule.setEtat(Etat.valueOf(etat));
        vehiculeRepository.save(vehicule);
    }
    @Override
    public Vehicule findVehiculeById(String id) throws VehiculeNotFound {
        Vehicule vehicule = vehiculeRepository.findByVin(id)
                .orElseThrow(() -> new VehiculeNotFound("Véhicule non trouvé avec ID " + id));

        vehicule.setProprietaire(clientRestClient.findClientById(vehicule.getClient_Id()));

        return vehicule;
    }

}
