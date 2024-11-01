package com.example.factureservice.service;

import com.example.factureservice.dto.FactureDto;
import com.example.factureservice.entities.Facture;
import com.example.factureservice.exception.FactureNotFound;

import java.util.List;

public interface FactureService {

    void saveFacture(FactureDto facDto);


    List<Facture> allFactures();

    Facture getFactureById(Long id) throws FactureNotFound;

    void envoyerEmailFacture(Long id);
}
