/*
 * Spring Boot Microservices tutorial - Tracing User service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TracingUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(TracingUserServiceApplication.class, args);
    }
}
