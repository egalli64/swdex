/*
 * Spring Boot Microservices tutorial - Circuit Breaker Order Service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BreakerOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BreakerOrderServiceApplication.class, args);
    }
}
