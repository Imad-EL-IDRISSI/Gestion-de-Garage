package com.example.notificationservice.clients;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "CLIENT-SERVICE")
public interface VehiculeRestClient {
}
