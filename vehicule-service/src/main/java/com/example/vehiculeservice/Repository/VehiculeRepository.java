package com.example.vehiculeservice.Repository;

import com.example.vehiculeservice.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
    Optional<Vehicule> findByVin(String id);
}
