package com.example.vehiculeservice.Repository;

import com.example.vehiculeservice.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculeRepository extends JpaRepository<Vehicule,Long> {
}
