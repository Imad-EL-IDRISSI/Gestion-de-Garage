package com.example.vehiculeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class VehiculeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculeServiceApplication.class, args);
	}

}
