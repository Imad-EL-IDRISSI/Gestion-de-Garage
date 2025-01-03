package com.example.factureservice.clients;

import com.example.factureservice.model.Maintenance;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "MAINTENANCE-SERVICE", url = "http://microservice-openfeign-maintenance-service-1:8083")
public interface MaintenanceRestMaintenance {

    @GetMapping("/maintenanceTasks/{id}")
    @CircuitBreaker(name = "maintenanceTasksService", fallbackMethod = "getDefaultMaintenanceTasks")
    Maintenance findMaintenanceById(@PathVariable Long id);
    @GetMapping("/maintenanceTasks")
    @CircuitBreaker(name = "maintenanceTasksService", fallbackMethod = "getAllMaintenanceTasks")
    List<Maintenance> allMaintenances();

    default Maintenance getDefaultMaintenanceTasks(Long id, Exception exception){
        Maintenance maintenance=new Maintenance();
        maintenance.setId(id);
        maintenance.setDesciption("Not Vailable");
        return  maintenance;
    }
    default List<Maintenance> getAllMaintenanceTasks(Exception exception){
        return List.of();
    }


}