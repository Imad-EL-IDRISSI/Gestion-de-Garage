package com.example.maintenance.service;

import com.example.maintenance.Client.VehiculeRestVehicule;
import com.example.maintenance.dto.MaintenanceDto;
import com.example.maintenance.entities.Maintenance;
import com.example.maintenance.exception.MaintenanceNotFound;
import com.example.maintenance.model.Vehicule;
import com.example.maintenance.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService{
    private MaintenanceRepository maintenanceRepository;
    private VehiculeRestVehicule vehiculeRestVehicule;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository, VehiculeRestVehicule vehiculeRestVehicule) {
        this.maintenanceRepository = maintenanceRepository;
        this.vehiculeRestVehicule = vehiculeRestVehicule;
    }
    @Override
    public List<Maintenance> getPlannedMaintenances() {
        return maintenanceRepository.findAll().stream()
                .filter(maintenance -> "PLANNED".equalsIgnoreCase(String.valueOf(maintenance.getStatut())))
                .collect(Collectors.toList());
    }
    @Override
    public List<Maintenance> getCompletedMaintenances() {
        return maintenanceRepository.findAll().stream()
                .filter(maintenance -> "COMPLETED".equalsIgnoreCase(String.valueOf(maintenance.getStatut())))
                .collect(Collectors.toList());
    }

    @Override
    public Maintenance addMaintenance(MaintenanceDto maintenanceDto) {
            Maintenance maintenance = new Maintenance();
            maintenance.setDebut(maintenanceDto.getDebut());
            maintenance.setFin(maintenanceDto.getFin());
            maintenance.setDescription(maintenanceDto.getDescription());
            maintenance.setVehiculeId(maintenanceDto.getVehiculeId());
            maintenance.setStatut(maintenanceDto.getStatut());

        return maintenanceRepository.save(maintenance);
    }

    @Override
    public Maintenance updateMaintenance(MaintenanceDto maintenanceDto, Long id) throws MaintenanceNotFound {
        Optional<Maintenance> existingMaintenance = maintenanceRepository.findById(id);
        if(existingMaintenance.isPresent()) {
            Maintenance maintenance = existingMaintenance.get();
            if(maintenanceDto.getDebut() != null){
                maintenance.setDebut(maintenanceDto.getDebut());
            }
            if(maintenanceDto.getFin() !=null){
                maintenance.setFin(maintenanceDto.getFin());
            }
            if(maintenanceDto.getDescription() !=null){
                maintenance.setDescription(maintenanceDto.getDescription());
            }
            if(maintenanceDto.getStatut() !=null){
                maintenance.setStatut(maintenanceDto.getStatut());
            }
            if(maintenanceDto.getVehiculeId() !=null){
                maintenance.setVehiculeId(maintenanceDto.getVehiculeId());
            }

            return maintenanceRepository.save(maintenance);
        }else {
            throw new MaintenanceNotFound("Maintenance task with Id "+id +"is not found");
        }
    }

    @Override
    public List<Maintenance> allMaintenances() {
        List<Maintenance> maintenanceList = maintenanceRepository.findAll();
        maintenanceList.forEach( m ->
                m.setVehicule(vehiculeRestVehicule.findVehiculeById(m.getVehiculeId())));

        return maintenanceList;
    }


    @Override
    public void deleteMaintenance(Long id) throws MaintenanceNotFound {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if(maintenance.isPresent()){
        maintenanceRepository.deleteById(id);
        }else {
            throw new MaintenanceNotFound("Maintenance task with Id "+id +"is not found");
        }
    }

    @Override
    public Maintenance findMaintenanceById(Long id) throws MaintenanceNotFound {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new MaintenanceNotFound("Véhicule non trouvé avec ID " + id));

        Vehicule vehicule = vehiculeRestVehicule.findVehiculeById(maintenance.getVehiculeId());
        System.out.println("---------------------" + vehicule);
        maintenance.setVehicule(vehiculeRestVehicule.findVehiculeById(maintenance.getVehiculeId()));
        maintenance.getVehicule().setProprietaire(vehicule.getProprietaire());

        return maintenance;
    }




}
