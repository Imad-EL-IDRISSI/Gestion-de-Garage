package com.example.vehiculeservice.service;

import com.example.vehiculeservice.dto.VehiculeDto;
import com.example.vehiculeservice.entities.Vehicule;
import com.example.vehiculeservice.exception.VehiculeNotFound;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VehiculeService {

    Vehicule saveVehicule(VehiculeDto vehiculeDto);
    Vehicule updateVehicule(VehiculeDto vehiculeDto, Long id) throws VehiculeNotFound;

    List<Vehicule> allVehicules();
}
