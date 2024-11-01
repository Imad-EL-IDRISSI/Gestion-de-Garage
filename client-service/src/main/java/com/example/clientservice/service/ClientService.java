package com.example.clientservice.service;

import com.example.clientservice.dto.ClientDto;
import com.example.clientservice.entities.Client;
import com.example.clientservice.exception.CustomerNotFound;
import com.example.clientservice.exception.EmailNonValideException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ClientService {

    Client saveClient(ClientDto clientDto) throws EmailNonValideException;


    Client updateClient(ClientDto clientDto, Long id) throws CustomerNotFound;

    List<Client> allClients();

    Client findClientById(String id);
}
