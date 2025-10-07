/*
 * Spring Boot Microservices tutorial - Random service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.random;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class RandomServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RandomServiceApplication.class, args);
    }
}
