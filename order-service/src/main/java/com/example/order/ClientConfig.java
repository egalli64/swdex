/*
 * Spring Boot Microservices tutorial - Order service
 * 
 * https://github.com/egalli64/spring-microservices
 */
package com.example.order;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Beans for Rest and Web Client.Builder
 */
@Configuration
public class ClientConfig {
    /**
     * The primary option is for the plain RestClient.Builder
     */
    @Bean
    @Primary
    RestClient.Builder plainRestClientBuilder() {
        return RestClient.builder();
    }

    /**
     * The load balanced version requires the qualified name to be accessed
     */
    @Bean
    @LoadBalanced
    RestClient.Builder loadBalancedRestClientBuilder() {
        return RestClient.builder();
    }

    /**
     * Only load balanced WebClient is used in this application
     */
    @Bean
    @LoadBalanced
    WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}
