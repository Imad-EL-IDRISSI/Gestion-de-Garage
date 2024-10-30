package com.example.maintenance.web;

import com.example.maintenance.dto.MaintenanceDto;
import com.example.maintenance.entities.Maintenance;
import com.example.maintenance.exception.MaintenanceNotFound;
import com.example.maintenance.service.MaintenanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maintenanceTasks")
public class MaintenanceController {
    private MaintenanceService maintenanceService;


    public MaintenanceController(MaintenanceService maintenanceService) {
        this.maintenanceService = maintenanceService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Maintenance> addMaintenanceTask(@RequestBody  MaintenanceDto maintenanceDto){
        Maintenance maintenance = maintenanceService.addMaintenance(maintenanceDto);
        return ResponseEntity.ok(maintenance);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Maintenance> updateMaintenanceTask(@RequestBody MaintenanceDto maintenanceDto,@PathVariable Long id) throws MaintenanceNotFound {
        Maintenance maintenance = maintenanceService.updateMaintenance(maintenanceDto,id);
        return ResponseEntity.ok(maintenance);
    }

    @GetMapping
    public ResponseEntity<List<Maintenance>> allMaintenanceTasks(){
        List<Maintenance> maintenanceList = maintenanceService.allMaintenances();
        return ResponseEntity.ok(maintenanceList);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMaintenanceTask(@PathVariable Long id) throws MaintenanceNotFound {
        maintenanceService.deleteMaintenance(id);
        return ResponseEntity.ok("Maintenance Task with id " + id +"  have been removed");
    }
    @GetMapping("/{id}")
    public ResponseEntity<Maintenance> getMaintenanceTask(@PathVariable Long id) throws MaintenanceNotFound {
        Maintenance maintenance = maintenanceService.getMaintenanceById(id);
        return ResponseEntity.ok(maintenance);
    }

}
