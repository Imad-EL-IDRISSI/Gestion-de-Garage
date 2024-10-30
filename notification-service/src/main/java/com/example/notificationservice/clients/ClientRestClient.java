package com.example.notificationservice.clients;


import com.example.notificationservice.model.Client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "CLIENT-SERVICE")
public interface ClientRestClient {
    @GetMapping("/clients/{id}")
    @CircuitBreaker(name = "clientService", fallbackMethod = "getDefaultClient")
    Client findClientById(@PathVariable Long id);
    @GetMapping("/clients")
    @CircuitBreaker(name = "clientService", fallbackMethod = "getAllClients")
    List<Client> allClients();

    default Client getDefaultClient(Long id, Exception exception){
        Client customer=new Client();
        customer.setId(id);
        customer.setNom("Not Vailable");
        customer.setAdresse("Not Vailable");
        customer.setEmail("Not Vailable");
        return customer;
    }
    default List<Client> getAllClients(Exception exception){
        return List.of();
    }
}
