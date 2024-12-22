package com.example.notificationservice.clients;

import com.example.notificationservice.enums.Etat;
import com.example.notificationservice.model.Maintenance;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@FeignClient(name = "MAINTENANCE-SERVICE", url = "http://microservice-openfeign-maintenance-service-1:8083")
public interface MaintenanceRestMaintenance {

    @GetMapping("/maintenanceTasks/{id}")
    @CircuitBreaker(name = "maintenanceTasksService", fallbackMethod = "getDefaultMaintenanceTasks")
    Maintenance findMaintenanceById(@PathVariable Long id);

    @GetMapping("/maintenanceTasks")
    @CircuitBreaker(name = "maintenanceTasksService", fallbackMethod = "getAllMaintenanceTasks")
    List<Maintenance> allMaintenances();

    // Nouvelle méthode pour mettre à jour l'état d'un véhicule
    @PutMapping("/vehicules/updateEtat/{id}")
    void updateVehiculeEtat(@PathVariable Long id, @RequestParam String etat);

    // Fallback methods
    default Maintenance getDefaultMaintenanceTasks(Long id, Exception exception) {
        Maintenance maintenance = new Maintenance();
        maintenance.setId(id);
        maintenance.setDesciption("Not Available");
        maintenance.setDebut(new Date("Not Available"));
        maintenance.setFin(new Date("Not Available"));
        maintenance.setStatut(Etat.valueOf("Not Available"));
        return maintenance;
    }

    default List<Maintenance> getAllMaintenanceTasks(Exception exception) {
        return List.of();
    }
}
