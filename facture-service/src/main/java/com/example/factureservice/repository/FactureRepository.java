package com.example.factureservice.repository;

import com.example.factureservice.entities.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture,Long> {


}
