/*
 * Spring Boot Microservices tutorial - Tracing Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TracingOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TracingOrderServiceApplication.class, args);
    }
}
