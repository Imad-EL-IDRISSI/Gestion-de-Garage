package com.example.notificationservice.clients;

import com.example.notificationservice.model.Client;
import com.example.notificationservice.model.Vehicule;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "VEHICULE-SERVICE", url = "http://microservice-openfeign-vehicule-service-1:8082")
public interface VehiculeRestClient {

    @GetMapping("/vehicules/{id}")
    @CircuitBreaker(name = "vehiculeService", fallbackMethod = "getDefaultVehicule")
    Vehicule findVehiculeById(@PathVariable Long id);
    @GetMapping("/vehicules")
    @CircuitBreaker(name = "vehiculeService", fallbackMethod = "getAllVehicules")
    List<Vehicule> allVehicules();



    default Vehicule getDefaultVehicule(Long id, Exception exception){
        Vehicule vehicule =new Vehicule();
        vehicule.setVin("Not Vailable");
        vehicule.setMarque("Not Vailable");
        vehicule.setEtat("Not Vailable");
        vehicule.setAnnee("Not Vailable");
        vehicule.setCouleur("Not Vailable");
        vehicule.setTypeCarb("Not Vailable");
        vehicule.setMarque("Not Vailable");
        vehicule.setEtat("Not Vailable");
        vehicule.setAnnee("Not Vailable");
        return vehicule;
    }
    default List<Client> getAllVehicules(Exception exception){
        return List.of();
    }
}


