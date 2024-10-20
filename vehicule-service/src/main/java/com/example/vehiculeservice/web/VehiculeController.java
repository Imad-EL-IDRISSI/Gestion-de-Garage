package com.example.vehiculeservice.web;

import com.example.vehiculeservice.dto.VehiculeDto;
import com.example.vehiculeservice.entities.Vehicule;
import com.example.vehiculeservice.exception.VehiculeNotFound;
import com.example.vehiculeservice.service.VehiculeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicule")
public class VehiculeController {

    private VehiculeService vehiculeService;

    public VehiculeController(VehiculeService vehiculeService) {
        this.vehiculeService = vehiculeService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Vehicule> saveVehicule(@RequestBody VehiculeDto vehiculeDto){
        Vehicule vehicule = vehiculeService.saveVehicule(vehiculeDto);
        return ResponseEntity.ok(vehicule);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Vehicule> updateVehicule(@RequestBody VehiculeDto vehiculeDto, @PathVariable Long id) throws VehiculeNotFound {
        Vehicule vehicule = vehiculeService.updateVehicule(vehiculeDto, id);
        return ResponseEntity.ok(vehicule);
    }

    @GetMapping
    public ResponseEntity<List<Vehicule>> allVehicule(){
        List<Vehicule> vehiculeList = vehiculeService.allVehicules();
        return ResponseEntity.ok(vehiculeList);
    }
}