package com.example.vehiculeservice.service;

import com.example.vehiculeservice.Repository.VehiculeRepository;
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

    public VehiculeServiceImpl(VehiculeRepository vehiculeRepository) {
        this.vehiculeRepository = vehiculeRepository;
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
        vehicule.setEtat(vehiculeDto.getEtat());


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
        return vehiculeRepository.findAll();
    }
}
