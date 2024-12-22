package com.example.clientservice.service;

import com.example.clientservice.dto.ClientDto;
import com.example.clientservice.entities.Client;
import com.example.clientservice.exception.CustomerNotFound;
import com.example.clientservice.exception.EmailNonValideException;
import com.example.clientservice.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{

    private ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }


    @Override
    public Client saveClient(ClientDto clientDto) throws EmailNonValideException{
        Client client = new Client();
        client.setCin(clientDto.getCin());
        client.setNom(clientDto.getNom());
        client.setPrenom(clientDto.getPrenom());
        client.setAdresse(clientDto.getAdresse());
        client.setTelephone(clientDto.getTelephone());
        client.setEmail(clientDto.getEmail());
        return clientRepository.save(client);
    }

    @Override
    public Client updateClient(ClientDto clientDto, String id) throws CustomerNotFound {
        Optional<Client> existingClient = clientRepository.findByCin(id);
        if(existingClient.isPresent()) {
            Client client = existingClient.get();
            if (clientDto.getCin() != null) {
                client.setCin(clientDto.getCin());
            }
            if (clientDto.getNom() != null) {
                client.setNom(clientDto.getNom());
            }
            if (clientDto.getPrenom() != null) {
                client.setPrenom(clientDto.getPrenom());
            }
            if (clientDto.getAdresse() != null) {
                client.setAdresse(clientDto.getAdresse());
            }
            if (clientDto.getTelephone() != null) {
                client.setTelephone(clientDto.getTelephone());
            }
            if (clientDto.getEmail() != null) {
                client.setEmail(clientDto.getEmail());
            }
            return clientRepository.save(client);
        }else{
            throw new CustomerNotFound("client introuvable avec l'ID:"+ id);
        }
    }

    @Override
    public List<Client> allClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client findClientById(String id) {
        Optional<Client> client = clientRepository.findByCin(id);
        return client.get();
    }
    @Override
    public List<String> getAllClientIds() {
        return clientRepository.findAll().stream()
                .map(Client::getCin)
                .collect(Collectors.toList());
    }


}
