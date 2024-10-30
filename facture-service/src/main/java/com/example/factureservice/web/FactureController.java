package com.example.factureservice.web;

import com.example.factureservice.dto.FactureDto;
import com.example.factureservice.entities.Facture;
import com.example.factureservice.service.FactureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/factures")
public class FactureController {

    private FactureService factureService;


    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }


    @PostMapping("/send")
    public ResponseEntity<String> sendFacture(@RequestBody FactureDto factureDto){
        factureService.envoyerEmailFacture(factureDto);
        return ResponseEntity.ok("Facture a ete envoyer");
    }
}
