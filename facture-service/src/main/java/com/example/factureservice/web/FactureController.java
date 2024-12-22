package com.example.factureservice.web;

import com.example.factureservice.dto.FactureDto;
import com.example.factureservice.entities.Facture;
import com.example.factureservice.exception.FactureNotFound;
import com.example.factureservice.service.FactureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/factures")

public class FactureController {

    private FactureService factureService;


    public FactureController(FactureService factureService) {
        this.factureService = factureService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveFacture(@RequestBody FactureDto factureDto){
        factureService.saveFacture(factureDto);
        return ResponseEntity.ok("Facture a ete envoyer");
    }

    @PostMapping("/send/{id}")
    public ResponseEntity<String> sendFacture(@PathVariable Long id){
        factureService.envoyerEmailFacture(id);
        return ResponseEntity.ok("Facture a ete envoyer");
    }
    @GetMapping
    public ResponseEntity<List<Facture>> allFactures(){
        List<Facture> factureList = factureService.allFactures();
        return ResponseEntity.ok(factureList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Facture> getFacture(@PathVariable Long id) throws FactureNotFound {
        return ResponseEntity.ok(factureService.getFactureById(id));

    }
}
