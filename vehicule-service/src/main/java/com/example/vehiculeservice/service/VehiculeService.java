package com.example.vehiculeservice.service;

import com.example.vehiculeservice.dto.VehiculeDto;
import com.example.vehiculeservice.entities.Vehicule;
import com.example.vehiculeservice.exception.VehiculeNotFound;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


public interface VehiculeService {

    Vehicule saveVehicule(VehiculeDto vehiculeDto);

    List<Vehicule> getVehiculesFonctionnels();

    Vehicule updateVehicule(VehiculeDto vehiculeDto, String id) throws VehiculeNotFound;

    List<Vehicule> allVehicules();



    void updateEtatVehicule(Long id, String etat);

    Vehicule findVehiculeById(String id) throws VehiculeNotFound;
}
