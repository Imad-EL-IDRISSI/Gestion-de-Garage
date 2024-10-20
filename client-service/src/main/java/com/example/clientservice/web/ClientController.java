package com.example.clientservice.web;

import com.example.clientservice.dto.ClientDto;
import com.example.clientservice.entities.Client;
import com.example.clientservice.exception.CustomerNotFound;
import com.example.clientservice.exception.EmailNonValideException;
import com.example.clientservice.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Client")
public class ClientController {

    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/ajouter")
    public ResponseEntity<Client> saveClient(@RequestBody ClientDto clientDto) throws EmailNonValideException {
        Client SClient = clientService.saveClient(clientDto);
        return ResponseEntity.ok(SClient);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<Client> updateClient(@RequestBody ClientDto clientDto, @PathVariable Long id) throws CustomerNotFound {
        Client UClient = clientService.updateClient(clientDto, id);
        return  ResponseEntity.ok(UClient);
    }

    @GetMapping()
    public ResponseEntity<List<Client>> allClient(){
        List<Client> clientList = clientService.allClients();
        return ResponseEntity.ok(clientList);
    }


}
