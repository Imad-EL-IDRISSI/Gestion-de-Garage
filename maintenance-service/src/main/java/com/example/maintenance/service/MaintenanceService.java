package com.example.maintenance.service;

import com.example.maintenance.dto.MaintenanceDto;
import com.example.maintenance.entities.Maintenance;
import com.example.maintenance.exception.MaintenanceNotFound;

import java.util.List;

public interface MaintenanceService {
    Maintenance addMaintenance(MaintenanceDto maintenanceDto);
    Maintenance updateMaintenance(MaintenanceDto maintenanceDto, Long id) throws MaintenanceNotFound;
    List<Maintenance> allMaintenances();
    void deleteMaintenance(Long id) throws MaintenanceNotFound;

    Maintenance findMaintenanceById(Long id)throws MaintenanceNotFound;

}
