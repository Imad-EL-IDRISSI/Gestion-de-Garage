package com.example.vehiculeservice.clients;

import com.example.vehiculeservice.model.Client;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;

@FeignClient(name = "CLIENT-SERVICE", url = "http://client-service-container:8081")
public interface ClientRestClient {

    @GetMapping("/clients/{id}")
    @CircuitBreaker(name = "clientService", fallbackMethod = "getDefaultClient")
    Client findClientById(@PathVariable String id);

    @GetMapping("/clients")
    @CircuitBreaker(name = "clientService", fallbackMethod = "getAllClientsFallback")
    List<Client> allClients();

    // Méthode de fallback pour findClientById
    default Client getDefaultClient(String id, Throwable exception) {
        Client customer = new Client();
        customer.setCin("Not Available");
        customer.setNom("Not Available");
        customer.setPrenom("Not Available");
        customer.setAdresse("Not Available");
        customer.setEmail("Not Available");
        customer.setTelephone("Not Available");

        return customer;
    }

    // Méthode de fallback pour allClients
    default List<Client> getAllClientsFallback(Throwable exception) {
        return Collections.emptyList();
    }
}
