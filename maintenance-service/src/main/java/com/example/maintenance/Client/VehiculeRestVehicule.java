package com.example.maintenance.Client;

import com.example.maintenance.model.Vehicule;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "VEHICULE-SERVICE", url = "http://localhost:8082")
public interface VehiculeRestVehicule {

    @GetMapping("/vehicules/{id}")
    @CircuitBreaker(name = "VehiculeService", fallbackMethod = "getDefaultVehicule")
    Vehicule findVehiculeById(@PathVariable String id);

    @GetMapping("/vehicules")
    @CircuitBreaker(name = "VehiculeService", fallbackMethod = "getAllVehicules")
    List<Vehicule> allVehicules();

    default Vehicule getDefaultVehicule(String id, Exception exception) {
        Vehicule vehicule = new Vehicule();
        vehicule.setVin("Not Available");
        vehicule.setMarque("Not Available");
        vehicule.setModele("Not Available");
        vehicule.setImmatriculation("Not Available");
        return vehicule;
    }

    default List<Vehicule> getAllVehicules(Exception exception) {
        return List.of();
    }
}
