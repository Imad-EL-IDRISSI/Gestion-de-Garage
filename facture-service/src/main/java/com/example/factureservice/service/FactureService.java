package com.example.factureservice.service;

import com.example.factureservice.dto.FactureDto;
import com.example.factureservice.entities.Facture;

public interface FactureService {

    void envoyerEmailFacture(FactureDto facDto);
}
