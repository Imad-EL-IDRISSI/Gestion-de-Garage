package com.example.maintenance.service;

import com.example.maintenance.dto.MaintenanceDto;
import com.example.maintenance.entities.Maintenance;
import com.example.maintenance.exception.MaintenanceNotFound;
import com.example.maintenance.model.Vehicule;
import com.example.maintenance.repository.MaintenanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaintenanceServiceImpl implements MaintenanceService{
    private MaintenanceRepository maintenanceRepository;

    public MaintenanceServiceImpl(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    @Override
    public Maintenance addMaintenance(MaintenanceDto maintenanceDto) {
            Maintenance maintenance = new Maintenance();
            maintenance.setDebut(maintenanceDto.getDebut());
            maintenance.setFin(maintenanceDto.getFin());
            maintenance.setDesciption(maintenanceDto.getDesciption());
            maintenance.setVehicule(maintenance.getVehicule());
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
            if(maintenanceDto.getDesciption() !=null){
                maintenance.setDesciption(maintenanceDto.getDesciption());
            }
            if(maintenanceDto.getStatut() !=null){
                maintenance.setStatut(maintenanceDto.getStatut());
            }
            if(maintenanceDto.getVehicule().getId() !=null){
                maintenance.getVehicule().setId(maintenanceDto.getVehicule().getId());
            }

            return maintenanceRepository.save(maintenance);
        }else {
            throw new MaintenanceNotFound("Maintenance task with Id "+id +"is not found");
        }
    }

    @Override
    public List<Maintenance> allMaintenances() {
        return maintenanceRepository.findAll();
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
    public Maintenance getMaintenanceById(Long id) throws MaintenanceNotFound {
        Optional<Maintenance> optionalMaintenance = maintenanceRepository.findById(id);
        if(optionalMaintenance.isPresent()){
            return optionalMaintenance.get();
        }else {
            throw new MaintenanceNotFound("Maintenance task with Id "+id +"is not found");
        }
    }

    @Override
    public Maintenance getMaintenanceByVehiculeId(Long id) throws MaintenanceNotFound {
        //List<Maintenance> optionalMaintenance = maintenanceRepository.findByVehiculeId(id);
        return null;
    }
}
