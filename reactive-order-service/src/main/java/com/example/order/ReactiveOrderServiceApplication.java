/*
 * Spring Boot Microservices tutorial - Reactive order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ReactiveOrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReactiveOrderServiceApplication.class, args);
    }
}
